package top.offsetmonkey538.monkeylib538.common.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandRegistrationApi;
import top.offsetmonkey538.monkeylib538.common.api.command.ConfigCommandApi;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.offsetconfig538.api.config.Config;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;
import top.offsetmonkey538.offsetconfig538.api.config.ErrorHandler;

import java.lang.reflect.Field;

import static top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi.argument;
import static top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi.literal;

public final class ConfigCommandImpl implements ConfigCommandApi {
    public ConfigCommandImpl() {

    }

    @Override
    public void registerConfigCommandImpl(ConfigHolder<?> configHolder, @Nullable Runnable configReloadCallback, @Nullable Runnable configValueSetCallback, String... commandTree) {
        @SuppressWarnings("unchecked")
        LiteralArgumentBuilder<Object> command = (LiteralArgumentBuilder<Object>) ConfigCommandApi.createConfigCommand(commandTree[commandTree.length - 1], configHolder, configReloadCallback, configValueSetCallback);
        for (int i = commandTree.length - 2; i >= 0; i--) {
            final LiteralArgumentBuilder<Object> parent = literal(commandTree[i]);
            parent.then(command);
            command = parent;
        }

        CommandRegistrationApi.registerCommand(command);
    }

    @Override
    public LiteralArgumentBuilder<?> createConfigCommandImpl(String commandName, ConfigHolder<?> configHolder, @Nullable Runnable configReloadCallback, @Nullable Runnable configValueSetCallback) {
        final LiteralArgumentBuilder<Object> rootCommand = literal(commandName).requires(CommandAbstractionApi::isAdmin);
        final String configName = configHolder.get().getId();

        // Reset command
        rootCommand.then(literal("reset").executes(ctx -> {
            // Resets the config to default
            configHolder.set(null);

            boolean[] failed = new boolean[]{false};
            ConfigManager.save(configHolder, createErrorHandler(ctx, configHolder, failed));
            if (failed[0]) return 0;

            CommandAbstractionApi.sendMessage(ctx, "Reset config '%s'.", configName);
            return Command.SINGLE_SUCCESS;
        }));

        // Reload command
        rootCommand.then(literal("reload").executes(ctx -> {
            boolean[] failed = new boolean[]{false};
            ConfigManager.init(configHolder, createErrorHandler(ctx, configHolder, failed));
            if (failed[0]) return 0;

            if (configReloadCallback != null) configReloadCallback.run();
            CommandAbstractionApi.sendMessage(ctx, "Reloaded config '%s'.", configName);
            return Command.SINGLE_SUCCESS;
        }));

        // Config fields
        final LiteralArgumentBuilder<Object> getCommand = literal("get");
        final LiteralArgumentBuilder<Object> setCommand = literal("set");

        for (Field field : configHolder.getConfigClass().getFields()) {
            getCommand.then(createGetCommandForField(configName, field, configHolder));
            setCommand.then(createSetCommandForField(configName, field, configHolder, configValueSetCallback));
        }

        rootCommand.then(getCommand);
        rootCommand.then(setCommand);

        return rootCommand;
    }

    private static <T extends Config> LiteralArgumentBuilder<Object> createGetCommandForField(final String configName, final Field field, final ConfigHolder<T> configHolder) {
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

    private static <T extends Config> LiteralArgumentBuilder<Object> createSetCommandForField(final String configName, final Field field, final ConfigHolder<T> configHolder, final @Nullable Runnable configValueSetCallback) {
        final String fieldName = field.getName();
        final LiteralArgumentBuilder<Object> thisSetCommand = literal(fieldName);
        final ArgumentType<?> fieldValueType = getType(configName, field.getType());

        if (fieldValueType == null) return thisSetCommand.executes(ctx -> {
            CommandAbstractionApi.sendText(
                    ctx,
                    MonkeyLibText.of("This config option is too complex to be modified through the command. Please instead modify the config file located at ")
                            .append(
                                    MonkeyLibText.of(configHolder.get().getFilePath().toAbsolutePath().toString())
                                            .applyStyle(style -> style
                                                    .withUnderline(true)
                                                    .withShowText(MonkeyLibText.of("Click to copy"))
                                                    .withCopyToClipboard(configHolder.get().getFilePath().toAbsolutePath().toString())
                                            )
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
                ConfigManager.save(configHolder, createErrorHandler(ctx, configHolder, failed));
                if (failed[0]) return 0;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            CommandAbstractionApi.sendMessage(ctx, "The value of '%s' in config '%s' has been changed from '%s' to '%s'!", fieldName, configName, originalValue, newValue);
            if (configValueSetCallback != null) configValueSetCallback.run();
            return Command.SINGLE_SUCCESS;
        }));
    }

    private static ErrorHandler createErrorHandler(final CommandContext<Object> ctx, final ConfigHolder<?> configHolder, final boolean[] failed) {
        return configHolder.getErrorHandler().andThen((error, throwable) -> {
            failed[0] = true;

            CommandAbstractionApi.sendError(ctx, "Error:\n%s", error);

            if (throwable != null) CommandAbstractionApi.sendError(ctx, "\nCause:\n%s", throwable);
        });
    }

    private static @Nullable ArgumentType<?> getType(String configName, Class<?> value) {
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
