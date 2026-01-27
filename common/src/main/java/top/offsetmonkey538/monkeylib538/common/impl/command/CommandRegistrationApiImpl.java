package top.offsetmonkey538.monkeylib538.common.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandRegistrationApi;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class CommandRegistrationApiImpl implements CommandRegistrationApi {
    private CommandRegistrationApiImpl() {}

    private static final Map<String, CommandNode<?>> commands = new HashMap<>();

    @Override
    public void registerCommandImpl(CommandNode<?> command) {
        registerCommand(command);
    }

    private static <T> void registerCommand(final CommandNode<T> command) {
        @SuppressWarnings("unchecked") // I'm sure it'll be fineeee
        final CommandNode<T> existingCommand = (CommandNode<T>) commands.get(command.getName());
        if (existingCommand == null) {
            commands.put(command.getName(), command);
            return;
        }
        for (final CommandNode<T> child : command.getChildren()) {
            existingCommand.addChild(child);
        }
    }

    @Override
    public Collection<CommandNode<?>> getCommandsImpl() {
        return commands.values();
    }
}
