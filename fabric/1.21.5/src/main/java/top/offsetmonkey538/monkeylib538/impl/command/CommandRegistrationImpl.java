package top.offsetmonkey538.monkeylib538.impl.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.command.CommandRegistrationApi;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistrationImpl implements CommandRegistrationApi {
    public static @Nullable List<LiteralArgumentBuilder<?>> commands = new ArrayList<>();


    @Override
    public void registerCommand(@NotNull LiteralArgumentBuilder<?> command) {
        if (commands == null) throw new IllegalStateException("Tried to register command after commands have been registered!");

        commands.add(command);
    }
}
