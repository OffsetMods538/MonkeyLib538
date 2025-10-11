package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Provides a method to register a {@link LiteralArgumentBuilder command} at startup.
 */
@ApiStatus.NonExtendable
public interface CommandRegistrationApi {
    /**
     * The instance
     */
    @ApiStatus.Internal
    CommandRegistrationApi INSTANCE = load(CommandRegistrationApi.class);

    /**
     * Adds the provided command to the list of commands to be registered at startup.
     *
     * @param command the command to register
     */
    static void registerCommand(final @NotNull LiteralArgumentBuilder<?> command) {
        INSTANCE.registerCommandImpl(command);
    }

    /**
     * Implementation of {@link #registerCommand(LiteralArgumentBuilder)}.
     *
     * @param command the command to register
     */
    @ApiStatus.Internal
    void registerCommandImpl(final @NotNull LiteralArgumentBuilder<?> command);
}
