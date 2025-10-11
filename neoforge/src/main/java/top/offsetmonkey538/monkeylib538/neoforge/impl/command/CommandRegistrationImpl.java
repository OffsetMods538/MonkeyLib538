package top.offsetmonkey538.monkeylib538.neoforge.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.command.CommandRegistrationApi;

import java.util.ArrayList;
import java.util.List;

public final class CommandRegistrationImpl implements CommandRegistrationApi {
    public static @NotNull List<LiteralArgumentBuilder<?>> commands = new ArrayList<>();

    @Override
    public void registerCommandImpl(final @NotNull LiteralArgumentBuilder<?> command) {
        commands.add(command);
    }
}
