package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Provides some abstractions for dealing with commands.
 * <br />
 * Mostly for internal use for the config command.
 * <p>
 *     An {@link Object} is used as the generic type for the argument builders, but the implementations ({@link #literalImpl(String)} and {@link #argumentImpl(String, ArgumentType)}) will create them with platform-specific command sources.
 *     <br />
 *     This way, the methods here can be called with a {@link CommandContext context} containing with the generic {@link Object} and implementations will cast that to the platform-specific command sources for implementing the methods.
 * </p>
 */
@ApiStatus.NonExtendable
public interface CommandAbstractionApi {
    /**
     * The instance
     */
    @ApiStatus.Internal
    CommandAbstractionApi INSTANCE = load(CommandAbstractionApi.class);

    /**
     * Creates a {@link LiteralArgumentBuilder} for the current platform specific command source.
     *
     * @param name the name of the {@link LiteralArgumentBuilder}
     * @return a {@link LiteralArgumentBuilder} for the current platform specific command source.
     */
    @SuppressWarnings("unchecked")
    static LiteralArgumentBuilder<Object> literal(final String name) {
        return (LiteralArgumentBuilder<Object>) INSTANCE.literalImpl(name);
    }

    /**
     * Creates a {@link RequiredArgumentBuilder} for the current platform specific command source and provided {@link ArgumentType}.
     *
     * @param name the name of the {@link RequiredArgumentBuilder}
     * @param type the {@link ArgumentType} to use.
     * @return a {@link RequiredArgumentBuilder} for the current platform specific command source and provided {@link ArgumentType}.
     * @param <T> the type for the {@link ArgumentType}
     */
    @SuppressWarnings("unchecked")
    static <T> RequiredArgumentBuilder<Object, T> argument(final String name, final ArgumentType<T> type) {
        return (RequiredArgumentBuilder<Object, T>) INSTANCE.argumentImpl(name, type);
    }

    /**
     * Sends a message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send, formatted using {@code args} and {@link String#formatted(Object...)}
     * @param args the args for formatting the message using {@link String#formatted(Object...)}
     */
    static void sendMessage(@NotNull final CommandContext<Object> ctx, @NotNull final String message, @NotNull final Object... args) {
        sendMessage(ctx, message.formatted(args));
    }
    /**
     * Sends a message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send
     */
    static void sendMessage(@NotNull final CommandContext<Object> ctx, @NotNull final String message) {
        INSTANCE.sendMessageImpl(ctx, message);
    }

    /**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send, formatted using {@link String#formatted(Object...)} with the provided {@code args}
     * @param args the args for formatting the error message using {@link String#formatted(Object...)}
     */
    static void sendError(@NotNull final CommandContext<Object> ctx, @NotNull final String message, @NotNull final Object... args) {
        sendError(ctx, message.formatted(args));
    }
    /**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send
     */
    static void sendError(@NotNull final CommandContext<Object> ctx, @NotNull final String message) {
        INSTANCE.sendErrorImpl(ctx, message);
    }

    /**
     * Sends a {@link MonkeyLibText} to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param text the {@link MonkeyLibText} to send
     */
    static void sendText(@NotNull final CommandContext<Object> ctx, @NotNull final MonkeyLibText text) {
        INSTANCE.sendTextImpl(ctx, text);
    }

    /**
     * Checks whether the provided command source is executed by a player.
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source is executed by a player.
     */
    static boolean executedByPlayer(final @NotNull Object source) {
        return INSTANCE.executedByPlayerImpl(source);
    }

    /**
     * Checks whether the provided command source has operator permissions or is the host of a singleplayer server.
     * <br>
     * This returns true for singleplayer hosts even when cheats are disabled.
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source has operator permissions or is the host of a singleplayer server
     * @see #isOp(Object)
     * @see #isHost(Object)
     */
    static boolean isAdmin(final @NotNull Object source) {
        return isOp(source) || isHost(source);
    }

    /**
     * Checks whether the provided command source has operator permissions.
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source has operator permissions
     * @see #isAdmin(Object)
     * @see #isHost(Object)
     */
    static boolean isOp(final @NotNull Object source) {
        return INSTANCE.isOpImpl(source);
    }

    /**
     * Checks whether the provided command source is the host of a singleplayer server.
     * <br>
     * Unlike {@link #isOp(Object)}, this method returns true even when cheats aren't enabled.
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source is the host of a singleplayer server
     * @see #isAdmin(Object)
     * @see #isOp(Object)
     */
    static boolean isHost(final @NotNull Object source) {
        return INSTANCE.isHostImpl(source);
    }


// TODO: applies to all Impl methods: don't write javadoc for them
    /**
     * Implementation of {@link #literal(String)}.
     *
     * @param name the name of the {@link LiteralArgumentBuilder}
     * @return a {@link LiteralArgumentBuilder} for the current-platform-specific command source.
     */
    @ApiStatus.Internal
    @NotNull LiteralArgumentBuilder<?> literalImpl(final @NotNull String name);
    /**
     * Implementation of {@link #argument(String, ArgumentType)}.
     *
     * @param name the name of the {@link LiteralArgumentBuilder}
     * @param type the {@link ArgumentType} to use.
     * @return a {@link RequiredArgumentBuilder} for the current-platform-specific command source.
     * @param <T> the type for the {@link ArgumentType}
     */
    @ApiStatus.Internal
    @NotNull <T> RequiredArgumentBuilder<?, T> argumentImpl(final @NotNull String name, final @NotNull ArgumentType<T> type);

    /**
     * Implementation of {@link #sendMessage(CommandContext, String)}.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send
     */
    @ApiStatus.Internal
    void sendMessageImpl(@NotNull final CommandContext<Object> ctx, @NotNull final String message);
    /**
     * Implementation of {@link #sendError(CommandContext, String)}.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send
     */
    @ApiStatus.Internal
    void sendErrorImpl(@NotNull final CommandContext<Object> ctx, @NotNull final String message);
    /**
     * Implementation of {@link #sendText(CommandContext, MonkeyLibText)}.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param text the {@link MonkeyLibText} to send
     */
    @ApiStatus.Internal
    void sendTextImpl(@NotNull final CommandContext<Object> ctx, @NotNull final MonkeyLibText text);

    /**
     * Implementation of {@link #executedByPlayer(Object)}.
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source is executed by a player.
     */
    @ApiStatus.Internal
    boolean executedByPlayerImpl(final @NotNull Object source);

    /**
     * Implementation of {@link #isOp(Object)}
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source has operator permissions
     */
    @ApiStatus.Internal
    boolean isOpImpl(final @NotNull Object source);

    /**
     * Implementation of {@link #isOp(Object)}
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source has operator permissions
     */
    @ApiStatus.Internal
    boolean isHostImpl(final @NotNull Object source);
}
