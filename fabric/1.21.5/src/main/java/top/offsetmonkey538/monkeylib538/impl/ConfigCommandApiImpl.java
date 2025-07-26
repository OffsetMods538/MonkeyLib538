package top.offsetmonkey538.monkeylib538.impl;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.api.ConfigCommandApi;
import top.offsetmonkey538.offsetconfig538.api.config.Config;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;
import top.offsetmonkey538.offsetconfig538.api.config.ErrorHandler;

import java.lang.reflect.Field;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

@ApiStatus.Internal
public class ConfigCommandApiImpl implements ConfigCommandApi {
    @ApiStatus.Internal
    public ConfigCommandApiImpl() {

    }

    @Override
    public @NotNull LiteralArgumentBuilder<ServerCommandSource> createConfigCommand(final @NotNull String name, final @NotNull ConfigHolder<?> configHolder) {
        final LiteralArgumentBuilder<ServerCommandSource> thisConfigCommand = literal(name);

        // Reset command
        thisConfigCommand.then(literal("reset").executes(context -> {
            // Resets the config to default
            configHolder.set(null);

            boolean[] failed = new boolean[]{false};
            ConfigManager.INSTANCE.save(configHolder, createErrorHandler(context, configHolder, failed));
            if (failed[0]) return 0;

            context.getSource().sendFeedback(() -> Text.literal(String.format("Reset config '%s'.", name)), true);
            return Command.SINGLE_SUCCESS;
        }));

        // Reload command
        thisConfigCommand.then(literal("reload").executes(context -> {
            boolean[] failed = new boolean[]{false};
            ConfigManager.INSTANCE.init(configHolder, createErrorHandler(context, configHolder, failed));
            if (failed[0]) return 0;

            context.getSource().sendFeedback(() -> Text.literal(String.format("Reloaded config '%s'.", name)), true);
            return Command.SINGLE_SUCCESS;
        }));

        // Config fields
        final LiteralArgumentBuilder<ServerCommandSource> getCommand = literal("get");
        final LiteralArgumentBuilder<ServerCommandSource> setCommand = literal("set");

        for (Field field : configHolder.getConfigClass().getFields()) {
            getCommand.then(createGetCommandForField(name, field, configHolder));
            setCommand.then(createSetCommandForField(name, field, configHolder));
        }

        thisConfigCommand.then(getCommand);
        thisConfigCommand.then(setCommand);

        return thisConfigCommand;
    }

    private static <T extends Config> LiteralArgumentBuilder<ServerCommandSource> createGetCommandForField(final @NotNull String configName, final @NotNull Field field, final @NotNull ConfigHolder<T> configHolder) {
        final String fieldName = field.getName();
        final LiteralArgumentBuilder<ServerCommandSource> thisGetCommand = literal(fieldName);

        return thisGetCommand.executes(context -> {
            final Object value;
            try {
                value = field.get(configHolder.get());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            context.getSource().sendFeedback(() -> Text.literal(String.format("The value of '%s' in config '%s' is '%s'", fieldName, configName, value)), true);
            return Command.SINGLE_SUCCESS;
        });
    }

    private static <T extends Config> LiteralArgumentBuilder<ServerCommandSource> createSetCommandForField(final @NotNull String configName, final @NotNull Field field, final @NotNull ConfigHolder<T> configHolder) {
        final String fieldName = field.getName();
        final LiteralArgumentBuilder<ServerCommandSource> thisSetCommand = literal(fieldName);
        final ArgumentType<?> fieldValueType = getType(configName, field.getType());

        if (fieldValueType == null) return thisSetCommand.executes(context -> {
            context.getSource().sendFeedback(
                    () -> Text.literal("This config option is too complex to be modified through the command. Please modify the config file located at ")
                            .append(
                                    Text.literal(configHolder.get().getFilePath().toAbsolutePath().toString())
                                            .setStyle(
                                                    Style.EMPTY
                                                            .withUnderline(true)
                                                            // todO: .withHoverEvent(new HoverEvent.ShowText(Text.literal("Click to copy")))
                                                            // todO: .withClickEvent(new ClickEvent.CopyToClipboard(configHolder.get().getFilePath().toAbsolutePath().toString()))
                                            )
                            ),
                    true
            );
            return Command.SINGLE_SUCCESS;
        });

        return thisSetCommand.then(argument("value", fieldValueType).executes(context -> {
            final Object originalValue;
            final Object newValue;
            try {
                originalValue = field.get(configHolder.get());
                newValue = context.getArgument("value", field.getType());

                field.set(configHolder.get(), newValue);

                // Try saving
                boolean[] failed = new boolean[]{false};
                ConfigManager.INSTANCE.save(configHolder, createErrorHandler(context, configHolder, failed));
                if (failed[0]) return 0;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            context.getSource().sendFeedback(() -> Text.literal(String.format("The value of '%s' in config '%s' has been changed from '%s' to '%s'!", fieldName, configName, originalValue, newValue)), true);
            return Command.SINGLE_SUCCESS;
        }));
    }

    private static @NotNull ErrorHandler createErrorHandler(final @NotNull CommandContext<ServerCommandSource> context, final @NotNull ConfigHolder<?> configHolder, final boolean[] failed) {
        return configHolder.getErrorHandler().andThen((error, throwable) -> {
            failed[0] = true;

            context.getSource().sendError(Text.of("Error:\n" + error));

            if (throwable != null) context.getSource().sendError(Text.of("\nCause:\n" + throwable));
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

        MonkeyLib538Common.getLogger().warn("Couldn't find suitable argument type for {} in config {}, ignoring!", value, configName);

        return null;
    }
}
