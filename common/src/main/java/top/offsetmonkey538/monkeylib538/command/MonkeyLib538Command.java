package top.offsetmonkey538.monkeylib538.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.api.ConfigCommandApi;
import top.offsetmonkey538.monkeylib538.impl.ConfigCommandApiImpl;
import top.offsetmonkey538.offsetconfig538.api.config.Config;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;
import top.offsetmonkey538.offsetconfig538.api.config.ErrorHandler;

import java.lang.reflect.Field;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;

public class MonkeyLib538Command {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        final ConfigCommandApiImpl configCommandApi = (ConfigCommandApiImpl) ConfigCommandApi.INSTANCE;
        configCommandApi.setInitialized();

        final LiteralArgumentBuilder<ServerCommandSource> monkeylib538Command = literal("monkeylib538");

        for (Map.Entry<String, ConfigHolder<?>> entry : configCommandApi.getConfigs().entrySet()) {
            final String configName = entry.getKey();
            registerCommandsForConfig(
                    dispatcher,
                    monkeylib538Command,
                    configName,
                    entry.getValue()
            );
        }
    }


    private static <T extends Config> void registerCommandsForConfig(
            final CommandDispatcher<ServerCommandSource> dispatcher,
            final LiteralArgumentBuilder<ServerCommandSource> monkeylib538Command,
            final String configName,
            final ConfigHolder<T> configHolder
            ) {
        final Class<T> configClass = configHolder.getConfigClass();

        for (Field configOption : configClass.getFields()) {
            final String configOptionName = configOption.getName();
            final ArgumentType<?> configOptionValueType = getType(configName, configOption.getType());

            // TODO: register different command for option without suitable argument type
            //  so that the player is notified that they need to manually modify the config for that.
            if (configOptionValueType == null) continue;
            dispatcher.register(monkeylib538Command.then(
                            literal("config")
                                    .requires(source -> source.hasPermissionLevel(2))
                                    .then(
                                            literal("reset").then(
                                                    literal(configName).executes(
                                                            context -> {
                                                                configHolder.set(null);
                                                                boolean[] failed = new boolean[] {false};
                                                                ConfigManager.INSTANCE.save(configHolder, createErrorHandler(context, configHolder, failed));
                                                                if (failed[0]) return 0;

                                                                context.getSource().sendFeedback(() -> Text.literal(String.format("Reset config '%s'.", configName)), true);
                                                                return Command.SINGLE_SUCCESS;
                                                            }
                                                    )
                                            )
                                    )
                                    .then(
                                            literal("reload").then(
                                                    literal(configName).executes(
                                                            context -> {
                                                                boolean[] failed = new boolean[] {false};
                                                                ConfigManager.INSTANCE.init(configHolder, createErrorHandler(context, configHolder, failed));
                                                                if (failed[0]) return 0;

                                                                context.getSource().sendFeedback(() -> Text.literal(String.format("Reloaded config '%s'.", configName)), true);
                                                                return Command.SINGLE_SUCCESS;
                                                            }
                                                    )
                                            )
                                    )
                                    .then(
                                            literal("get").then(
                                                    literal(configName).then(
                                                            literal(configOptionName).executes(
                                                                    context -> {
                                                                        final Object value;
                                                                        try {
                                                                            value = configOption.get(configHolder.get());
                                                                        } catch (IllegalAccessException e) {
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                        context.getSource().sendFeedback(() -> Text.literal(String.format("The value of '%s' in config '%s' is '%s'", configOptionName, configName, value)), true);
                                                                        return Command.SINGLE_SUCCESS;
                                                                    }
                                                            )
                                                    )
                                            )
                                    ).then(
                                            literal("set").then(
                                                    literal(configName).then(
                                                            literal(configOptionName).then(
                                                                    argument("value", configOptionValueType).executes(
                                                                            context -> {
                                                                                final Object originalValue;
                                                                                final Object newValue;
                                                                                try {
                                                                                    originalValue = configOption.get(configHolder.get());

                                                                                    newValue = context.getArgument("value", configOption.getType());

                                                                                    configOption.set(configHolder.get(), newValue);

                                                                                    boolean[] failed = new boolean[] {false};
                                                                                    ConfigManager.INSTANCE.save(configHolder, createErrorHandler(context, configHolder, failed));
                                                                                    if (failed[0]) return 0;
                                                                                } catch (IllegalAccessException e) {
                                                                                    throw new RuntimeException(e);
                                                                                }

                                                                                context.getSource().sendFeedback(() -> Text.literal(String.format("The value of '%s' in config '%s' has been changed from '%s' to '%s'!", configOptionName, configName, originalValue, newValue)), true);

                                                                                return Command.SINGLE_SUCCESS;
                                                                            }
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                    )
            );
        }
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

        MonkeyLib538Common.LOGGER.warn("Couldn't find suitable argument type for {} in config {}, ignoring!", value, configName);

        return null;
    }
}
