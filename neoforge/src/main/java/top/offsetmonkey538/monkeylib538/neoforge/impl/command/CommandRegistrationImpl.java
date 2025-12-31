package top.offsetmonkey538.monkeylib538.neoforge.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandRegistrationApi;

import java.util.ArrayList;
import java.util.List;

public final class CommandRegistrationImpl implements CommandRegistrationApi {
    public static List<LiteralArgumentBuilder<?>> commands = new ArrayList<>();

    @Override
    public void registerCommandImpl(final LiteralArgumentBuilder<?> command) {
        commands.add(command);
    }
}
