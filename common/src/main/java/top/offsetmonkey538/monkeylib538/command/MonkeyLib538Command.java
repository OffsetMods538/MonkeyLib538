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

import java.util.HashMap;
import java.util.Map;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;
import static top.offsetmonkey538.monkeylib538.config.ConfigManager.CONFIGS;

public class MonkeyLib538Command {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        final LiteralArgumentBuilder<ServerCommandSource> monkeylib538Command = literal("monkeylib538");


        for (Map.Entry<String, Config> entry : CONFIGS.entrySet()) {
            final String configName = entry.getKey();
            final Config config = entry.getValue();
            for (Map.Entry<String, Object> configOption : config.getValuesAsEntries().entrySet()) {
                final String configOptionName = configOption.getKey();
                final Object configOptionValue = configOption.getValue();
                final ArgumentType<?> configOptionValueType = getType(config, configOptionValue);

                if (configOptionValueType == null) continue;

                dispatcher.register(monkeylib538Command.then(
                        literal("config")
                                .requires(source -> source.hasPermissionLevel(2))
                                .then(
                                        literal(configName).then(
                                                literal(configOptionName).executes(
                                                        context -> {
                                                           final Object value = CONFIGS.get(configName).getValuesAsEntries().get(configOptionName);
                                                           context.getSource().sendFeedback(() -> Text.literal(String.format("The value of '%s' in config '%s' is '%s'", configOptionName, configName, value)), false);
                                                           return Command.SINGLE_SUCCESS;
                                                        }
                                                ).then(
                                                        argument("value", configOptionValueType).executes(
                                                                context -> {
                                                                    final Object originalValue = CONFIGS.get(configName).getValuesAsEntries().get(configOptionName);
                                                                    final Object value = context.getArgument("value", configOptionValue.getClass());
                                                                    final Config configg = CONFIGS.get(configName);
                                                                    final HashMap<String, Object> configOptions = configg.getValuesAsEntries();
                                                                    configOptions.put(configOptionName, value);
                                                                    configg.setValuesFromEntries(configOptions);
                                                                    ConfigManager.save(configg, (error, exception) -> {
                                                                        throw new RuntimeException(error, exception);
                                                                    });

                                                                    context.getSource().sendFeedback(() -> Text.literal(String.format("The value of '%s' in config '%s' has been changed from '%s' to '%s'!", configOptionName, configName, originalValue, value)), true);

                                                                    return Command.SINGLE_SUCCESS;
                                                                }
                                                        )
                                                )
                                        )
                                )
                        )
                );
            }
        }
    }

    private static <T> ArgumentType<?> getType(Config config, T value) {
        if (value instanceof Byte) return IntegerArgumentType.integer(Byte.MIN_VALUE, Byte.MAX_VALUE);
        if (value instanceof Short) return IntegerArgumentType.integer(Short.MIN_VALUE, Short.MAX_VALUE);
        if (value instanceof Integer) return IntegerArgumentType.integer();
        if (value instanceof Long) return LongArgumentType.longArg();
        if (value instanceof Float) return FloatArgumentType.floatArg();
        if (value instanceof Double) return DoubleArgumentType.doubleArg();
        if (value instanceof Boolean) return BoolArgumentType.bool();

        if (value instanceof String) return StringArgumentType.string();

        MonkeyLib538Common.LOGGER.warn("Couldn't find suitable argument type for {} in config {}, ignoring!", value, config);

        return null;
    }
}
