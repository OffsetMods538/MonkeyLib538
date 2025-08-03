package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Provides a method to register a {@link LiteralArgumentBuilder command} at startup.
 */
public interface CommandRegistrationApi {
    /**
     * The instance
     */
    CommandRegistrationApi INSTANCE = load(CommandRegistrationApi.class);

    /**
     * Adds the provided command to the list of commands to be registered at startup.
     * <br />
     * <strong>Calling this after command registration finishes will result in a {@link IllegalStateException}!</strong>
     *
     * @param command the command to register
     */
    void registerCommand(final @NotNull LiteralArgumentBuilder<?> command);
}
