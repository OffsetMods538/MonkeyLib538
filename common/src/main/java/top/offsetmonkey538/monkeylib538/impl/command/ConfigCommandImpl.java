package top.offsetmonkey538.monkeylib538.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.api.command.CommandRegistrationApi;
import top.offsetmonkey538.monkeylib538.api.command.ConfigCommandApi;
import top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.api.text.TextApi;
import top.offsetmonkey538.offsetconfig538.api.config.Config;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;
import top.offsetmonkey538.offsetconfig538.api.config.ErrorHandler;

import java.lang.reflect.Field;

import static top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi.argument;
import static top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi.literal;

/**
 * Implementation of {@link ConfigCommandApi}
 */
@ApiStatus.Internal
public final class ConfigCommandImpl implements ConfigCommandApi {

    /**
     * Public no-args constructor for java service magic to call
     */
    @ApiStatus.Internal
    public ConfigCommandImpl() {

    }

    @Override
    public void registerConfigCommand(final @NotNull ConfigHolder<?> configHolder, final @NotNull String... commandTree) {
        @SuppressWarnings("unchecked")
        LiteralArgumentBuilder<Object> command = (LiteralArgumentBuilder<Object>) ConfigCommandApi.INSTANCE.createConfigCommand(commandTree[commandTree.length - 1], configHolder);
        for (int i = commandTree.length - 2; i >= 0; i--) {
            final LiteralArgumentBuilder<Object> parent = literal(commandTree[i]);
            parent.then(command);
            command = parent;
        }

        CommandRegistrationApi.INSTANCE.registerCommand(command);
    }

    public @NotNull LiteralArgumentBuilder<Object> createConfigCommand(final @NotNull String commandName, final @NotNull ConfigHolder<?> configHolder) {
        final LiteralArgumentBuilder<Object> rootCommand = literal(commandName);
        final String configName = configHolder.get().getId();

        // Reset command
        rootCommand.then(literal("reset").executes(ctx -> {
            // Resets the config to default
            configHolder.set(null);

            boolean[] failed = new boolean[]{false};
            ConfigManager.INSTANCE.save(configHolder, createErrorHandler(ctx, configHolder, failed));
            if (failed[0]) return 0;

            CommandAbstractionApi.sendMessage(ctx, "Reset config '%s'.", configName);
            return Command.SINGLE_SUCCESS;
        }));

        // Reload command
        rootCommand.then(literal("reload").executes(ctx -> {
            boolean[] failed = new boolean[]{false};
            ConfigManager.INSTANCE.init(configHolder, createErrorHandler(ctx, configHolder, failed));
            if (failed[0]) return 0;

            CommandAbstractionApi.sendMessage(ctx, "Reloaded config '%s'.", configName);
            return Command.SINGLE_SUCCESS;
        }));

        // Config fields
        final LiteralArgumentBuilder<Object> getCommand = literal("get");
        final LiteralArgumentBuilder<Object> setCommand = literal("set");

        for (Field field : configHolder.getConfigClass().getFields()) {
            getCommand.then(createGetCommandForField(configName, field, configHolder));
            setCommand.then(createSetCommandForField(configName, field, configHolder));
        }

        rootCommand.then(getCommand);
        rootCommand.then(setCommand);

        return rootCommand;
    }

    private static <T extends Config> LiteralArgumentBuilder<Object> createGetCommandForField(final @NotNull String configName, final @NotNull Field field, final @NotNull ConfigHolder<T> configHolder) {
        final String fieldName = field.getName();
        final LiteralArgumentBuilder<Object> thisGetCommand = literal(fieldName);

        return thisGetCommand.executes(ctx -> {
            final Object value;
            try {
                value = field.get(configHolder.get());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            CommandAbstractionApi.sendMessage(ctx, "The value of '%s' in config '%s' is '%s'", fieldName, configName, value);
            return Command.SINGLE_SUCCESS;
        });
    }

    private static <T extends Config> LiteralArgumentBuilder<Object> createSetCommandForField(final @NotNull String configName, final @NotNull Field field, final @NotNull ConfigHolder<T> configHolder) {
        final String fieldName = field.getName();
        final LiteralArgumentBuilder<Object> thisSetCommand = literal(fieldName);
        final ArgumentType<?> fieldValueType = getType(configName, field.getType());

        if (fieldValueType == null) return thisSetCommand.executes(ctx -> {
            CommandAbstractionApi.sendText(
                    ctx,
                    TextApi.INSTANCE.of("This config option is too complex to be modified through the command. Please instead modify the config file located at ")
                            .append(
                                    TextApi.INSTANCE.of(configHolder.get().getFilePath().toAbsolutePath().toString())
                                            .setUnderlined(true)
                                            .showTextOnHover(TextApi.INSTANCE.of("Click to copy"))
                                            .copyStringOnClick(configHolder.get().getFilePath().toAbsolutePath().toString())
                            )
            );
            return Command.SINGLE_SUCCESS;
        });

        return thisSetCommand.then(argument("value", fieldValueType).executes(ctx -> {
            final Object originalValue;
            final Object newValue;
            try {
                originalValue = field.get(configHolder.get());
                newValue = ctx.getArgument("value", field.getType());

                field.set(configHolder.get(), newValue);

                // Try saving
                boolean[] failed = new boolean[]{false};
                ConfigManager.INSTANCE.save(configHolder, createErrorHandler(ctx, configHolder, failed));
                if (failed[0]) return 0;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            CommandAbstractionApi.sendMessage(ctx, "The value of '%s' in config '%s' has been changed from '%s' to '%s'!", fieldName, configName, originalValue, newValue);
            return Command.SINGLE_SUCCESS;
        }));
    }

    private static @NotNull ErrorHandler createErrorHandler(final @NotNull CommandContext<Object> ctx, final @NotNull ConfigHolder<?> configHolder, final boolean[] failed) {
        return configHolder.getErrorHandler().andThen((error, throwable) -> {
            failed[0] = true;

            CommandAbstractionApi.sendError(ctx, "Error:\n%s", error);

            if (throwable != null) CommandAbstractionApi.sendError(ctx, "\nCause:\n%s", throwable);
        });
    }

    private static ArgumentType<?> getType(String configName, Class<?> value) {
        // TODO: I think items and entities and vectors and stuff have their own argument type so add them as well
        if (value.isAssignableFrom(Byte.class) || value.isAssignableFrom(byte.class)) return IntegerArgumentType.integer(Byte.MIN_VALUE, Byte.MAX_VALUE);
        if (value.isAssignableFrom(Short.class) || value.isAssignableFrom(short.class)) return IntegerArgumentType.integer(Short.MIN_VALUE, Short.MAX_VALUE);
        if (value.isAssignableFrom(Integer.class) || value.isAssignableFrom(int.class)) return IntegerArgumentType.integer();
        if (value.isAssignableFrom(Long.class) || value.isAssignableFrom(long.class)) return LongArgumentType.longArg();
        if (value.isAssignableFrom(Float.class) || value.isAssignableFrom(float.class)) return FloatArgumentType.floatArg();
        if (value.isAssignableFrom(Double.class) || value.isAssignableFrom(double.class)) return DoubleArgumentType.doubleArg();
        if (value.isAssignableFrom(Boolean.class) || value.isAssignableFrom(boolean.class)) return BoolArgumentType.bool();

        if (value.isAssignableFrom(String.class)) return StringArgumentType.string();

        MonkeyLib538Common.getLogger().warn("Couldn't find suitable argument type for class '%s' in config '%s', ignoring!", value.getName(), configName);

        return null;
    }
}
