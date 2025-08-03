package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface CommandRegistrationApi {
    CommandRegistrationApi INSTANCE = load(CommandRegistrationApi.class);

    void registerCommand(final @NotNull LiteralArgumentBuilder<?> command);
}
