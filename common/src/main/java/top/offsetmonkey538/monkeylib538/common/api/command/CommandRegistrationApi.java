package top.offsetmonkey538.monkeylib538.common.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

/**
 * Provides a method to register a {@link LiteralArgumentBuilder command} at startup.
 */
public interface CommandRegistrationApi {
    CommandRegistrationApi INSTANCE = load(CommandRegistrationApi.class);

    /**
     * Adds the provided command to the list of commands to be registered at startup.
     *
     * @param command the command to register
     */
    static void registerCommand(final LiteralArgumentBuilder<?> command) {
        INSTANCE.registerCommandImpl(command);
    }


    void registerCommandImpl(final LiteralArgumentBuilder<?> command);
}
