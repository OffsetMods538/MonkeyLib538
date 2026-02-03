package top.offsetmonkey538.monkeylib538.common.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandRegistrationApi;
import top.offsetmonkey538.monkeylib538.common.api.command.ConfigCommandApi;
import top.offsetmonkey538.offsetutils538.api.config.Config;
import top.offsetmonkey538.offsetutils538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetutils538.api.config.ConfigManager;
import top.offsetmonkey538.offsetutils538.api.log.ErrorHandler;

import java.lang.reflect.Field;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.LOGGER;
import static top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi.argument;
import static top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi.literal;
import static top.offsetmonkey538.offsetutils538.api.text.ArgReplacer.replaceArgs;

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
        final String configName = configHolder.toString();

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

        // Location command
        rootCommand.then(literal("location").executes(ctx -> {
            final String filePath = configHolder.get().getFilePath().toAbsolutePath().toString();
            CommandAbstractionApi.sendText(ctx, Component
                    .text(replaceArgs("Config '%s' is located at ", configHolder))
                    .append(Component
                            .text(filePath)
                            .style(style -> style
                                    .decorate(TextDecoration.UNDERLINED)
                                    .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                                    .clickEvent(ClickEvent.copyToClipboard(filePath))
                            )
                    )
            );
            return Command.SINGLE_SUCCESS;
        }));

        // Config fields
        final LiteralArgumentBuilder<Object> getCommand = literal("get");
        final LiteralArgumentBuilder<Object> setCommand = literal("set");

        addGetSetCommandsForClass(getCommand, setCommand, configHolder.getConfigClass(), configHolder, configValueSetCallback);

        rootCommand.then(getCommand);
        rootCommand.then(setCommand);

        return rootCommand;
    }

    private static <T extends Config> void addGetSetCommandsForClass(final LiteralArgumentBuilder<Object> getCommand, final LiteralArgumentBuilder<Object> setCommand, final Class<?> fieldsHolder, final ConfigHolder<T> configHolder, final @Nullable Runnable configValueSetCallback) {
        addGetSetCommandsForClass(getCommand, setCommand, fieldsHolder, configHolder, configValueSetCallback, configHolder::get, null);
    }

    private static <T extends Config> void addGetSetCommandsForClass(final LiteralArgumentBuilder<Object> getCommand, final LiteralArgumentBuilder<Object> setCommand, final Class<?> fieldsHolder, final ConfigHolder<T> configHolder, final @Nullable Runnable configValueSetCallback, final FieldParentGetter fieldParentGetter, final @Nullable String fieldNamePrefix) {
        if (fieldsHolder.getFields().length == 0) {
            LOGGER.warn("Can't unpack class '%s' in config '%s' from tree '%s' as it doesn't contain any fields, ignoring!", fieldsHolder.getName(), configHolder, fieldNamePrefix);
            if (fieldNamePrefix == null) {
                LOGGER.error("The above error happened at the root of the config? Something is...... wrong");
                return;
            }

            final LiteralArgumentBuilder<Object> command = literal(fieldNamePrefix).executes(context -> {
                CommandAbstractionApi.sendText(context, Component
                        .text("The value of '%s' in config '%s' is too complex for the command. Please see the config manually at ").append(Component
                                .text(replaceArgs("[%s]", configHolder.get().getFilePath().toAbsolutePath().toString())).style(style -> style
                                        .decorate(TextDecoration.UNDERLINED)
                                        .hoverEvent(HoverEvent.showText(Component.text("Click to copy")))
                                        .clickEvent(ClickEvent.copyToClipboard(configHolder.get().getFilePath().toAbsolutePath().toString()))
                                )
                        ).style(style -> style
                                .color(NamedTextColor.RED)
                        )
                );
                return 0;
            });
            getCommand.then(command);
            setCommand.then(command);
            return;
        }

        for (final Field field : fieldsHolder.getFields()) {
            final String fieldName = fieldNamePrefix == null ? field.getName() : fieldNamePrefix + field.getName();
            LOGGER.error(replaceArgs("Getting argument type for '%s' for field '%s' in config '%s'", field.getType(), field.getName(), configHolder));
            final ArgumentType<?> fieldValueType = getType(configHolder.toString(), field.getType());

            if (fieldValueType == null) {
                addGetSetCommandsForClass(getCommand, setCommand, field.getType(), configHolder, configValueSetCallback, fieldParentGetter.adopt(field),  fieldName + ".");
                continue;
            }

            // This field can be used normally in a command
            getCommand.then(createGetCommandForField(fieldName, field, fieldParentGetter, configHolder));
            setCommand.then(createSetCommandForField(fieldName, field, fieldValueType, fieldParentGetter, configHolder, configValueSetCallback));
        }
    }

    private static <T extends Config> LiteralArgumentBuilder<Object> createGetCommandForField(final String fieldName, final Field field, final FieldParentGetter fieldParentGetter, final ConfigHolder<T> configHolder) {
        return literal(fieldName).executes(ctx -> {
            final Object value;
            try {
                value = field.get(fieldParentGetter.get());
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            CommandAbstractionApi.sendMessage(ctx, "The value of '%s' in config '%s' is '%s'", fieldName, configHolder, value);
            return Command.SINGLE_SUCCESS;
        });
    }

    private static <T extends Config> LiteralArgumentBuilder<Object> createSetCommandForField(final String fieldName, final Field field, final ArgumentType<?> fieldValueType, final FieldParentGetter fieldParentGetter, final ConfigHolder<T> configHolder, final @Nullable Runnable configValueSetCallback) {
        return literal(fieldName).then(argument("value", fieldValueType).executes(ctx -> {
            final Object originalValue;
            final Object newValue;
            try {
                originalValue = field.get(fieldParentGetter.get());
                newValue = ctx.getArgument("value", field.getType());

                field.set(fieldParentGetter.get(), newValue);

                // Try saving
                boolean[] failed = new boolean[]{false};
                ConfigManager.save(configHolder, createErrorHandler(ctx, configHolder, failed));
                if (failed[0]) return 0;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            CommandAbstractionApi.sendMessage(ctx, "The value of '%s' in config '%s' has been changed from '%s' to '%s'!", fieldName, configHolder, originalValue, newValue);
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

    private static @Nullable ArgumentType<?> getType(final String configName, final Class<?> value) {
        // TODO: I think items and entities and vectors and stuff have their own argument type so add them as well
        if (value.isAssignableFrom(Byte.class) || value.isAssignableFrom(byte.class)) return IntegerArgumentType.integer(Byte.MIN_VALUE, Byte.MAX_VALUE);
        if (value.isAssignableFrom(Short.class) || value.isAssignableFrom(short.class)) return IntegerArgumentType.integer(Short.MIN_VALUE, Short.MAX_VALUE);
        if (value.isAssignableFrom(Integer.class) || value.isAssignableFrom(int.class)) return IntegerArgumentType.integer();
        if (value.isAssignableFrom(Long.class) || value.isAssignableFrom(long.class)) return LongArgumentType.longArg();
        if (value.isAssignableFrom(Float.class) || value.isAssignableFrom(float.class)) return FloatArgumentType.floatArg();
        if (value.isAssignableFrom(Double.class) || value.isAssignableFrom(double.class)) return DoubleArgumentType.doubleArg();
        if (value.isAssignableFrom(Boolean.class) || value.isAssignableFrom(boolean.class)) return BoolArgumentType.bool();

        if (value.isAssignableFrom(String.class)) return StringArgumentType.string();

        LOGGER.info("Couldn't find suitable argument type for class '%s' in config '%s'! Unpacking from fields...", value.getName(), configName);

        return null;
    }

    @FunctionalInterface
    private interface FieldParentGetter {
        Object get() throws IllegalAccessException;

        default FieldParentGetter adopt(Field newField) {
            return () -> newField.get(this.get());
        }
    }
}
