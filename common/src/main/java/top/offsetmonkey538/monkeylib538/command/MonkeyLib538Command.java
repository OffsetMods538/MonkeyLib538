package top.offsetmonkey538.monkeylib538.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import top.offsetmonkey538.monkeylib538.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.config.Config;
import top.offsetmonkey538.monkeylib538.config.ConfigManager;

import java.lang.reflect.Field;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static top.offsetmonkey538.monkeylib538.config.ConfigManager.CONFIGS;

public class MonkeyLib538Command {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        final LiteralArgumentBuilder<ServerCommandSource> monkeylib538Command = literal("monkeylib538");


        for (Map.Entry<String, Config<?>> entry : CONFIGS.entrySet()) {
            final String configName = entry.getKey();
            registerCommandsForConfig(
                    dispatcher,
                    monkeylib538Command,
                    configName,
                    entry.getValue()
            );
        }
    }

    private static <T extends Config<?>> void registerCommandsForConfig(
            final CommandDispatcher<ServerCommandSource> dispatcher,
            final LiteralArgumentBuilder<ServerCommandSource> monkeylib538Command,
            final String configName,
            final T config
            ) {
        //noinspection unchecked
        final Class<T> configClass = (Class<T>) config.getClass();
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
                                                                ConfigManager.save(config.getDefaultConfig(), (error, exception) -> {
                                                                    throw new RuntimeException(error, exception);
                                                                });
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
                                                                ConfigManager.init(config.getDefaultConfig(), (error, exception) -> {
                                                                    throw new RuntimeException(error, exception);
                                                                });
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
                                                                            value = configOption.get(CONFIGS.get(configName));
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
                                                                                    originalValue = configOption.get(CONFIGS.get(configName));

                                                                                    newValue = context.getArgument("value", configOption.getType());
                                                                                    //noinspection unchecked
                                                                                    final T configg = (T) CONFIGS.get(configName);
                                                                                    configOption.set(configg, newValue);
                                                                                    ConfigManager.save(configg, (error, exception) -> {
                                                                                        throw new RuntimeException(error, exception);
                                                                                    });
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
