package top.offsetmonkey538.monkeylib538.common.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

import java.util.Collection;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

/**
 * Provides a method to register a {@link LiteralArgumentBuilder command} at startup.
 */
public interface CommandRegistrationApi {
    @Internal
    CommandRegistrationApi INSTANCE = load(CommandRegistrationApi.class);

    /**
     * Adds the provided command to the list of commands to be registered at startup.
     *
     * @param command the command to register
     */
    static void registerCommand(final LiteralArgumentBuilder<?> command) {
        registerCommand(command.build());
    }

    /**
     * Adds the provided command to the list of commands to be registered at startup.
     *
     * @param command the command to register
     */
    static void registerCommand(final CommandNode<?> command) {
        INSTANCE.registerCommandImpl(command);
    }

    static Collection<CommandNode<?>> getCommands() {
        return INSTANCE.getCommandsImpl();
    }


    @Internal void registerCommandImpl(final CommandNode<?> command);
    @Internal Collection<CommandNode<?>> getCommandsImpl();
}
