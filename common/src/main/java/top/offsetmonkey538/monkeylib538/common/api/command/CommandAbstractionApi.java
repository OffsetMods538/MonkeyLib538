package top.offsetmonkey538.monkeylib538.common.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

/**
 * Provides some abstractions for dealing with commands.
 * <br />
 * Mostly for internal use for the config command.
 *
 * <p>An {@link Object} is used as the generic type for the argument builders, but the implementations ({@link #literalImpl(String)} and {@link #argumentImpl(String, ArgumentType)}) will create them with platform-specific command sources.
 * <br />
 * This way, the methods here can be called with a {@link CommandContext context} containing with the generic {@link Object} and implementations will cast that to the platform-specific command sources for implementing the methods.
 * </p>
 */
public interface CommandAbstractionApi {
    @Internal
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
     * @param <T> the type for the {@link ArgumentType}
     * @return a {@link RequiredArgumentBuilder} for the current platform specific command source and provided {@link ArgumentType}.
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
    static void sendMessage(final CommandContext<Object> ctx, final String message, final Object... args) {
        sendMessage(ctx, message.formatted(args));
    }
    /**
     * Sends a message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send
     */
    static void sendMessage(final CommandContext<Object> ctx, final String message) {
        INSTANCE.sendMessageImpl(ctx, message);
    }

    /**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send, formatted using {@link String#formatted(Object...)} with the provided {@code args}
     * @param args the args for formatting the error message using {@link String#formatted(Object...)}
     */
    static void sendError(final CommandContext<Object> ctx, final String message, final Object... args) {
        sendError(ctx, message.formatted(args));
    }
    /**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send
     */
    static void sendError(final CommandContext<Object> ctx, final String message) {
        INSTANCE.sendErrorImpl(ctx, message);
    }

    /**
     * Sends a {@link MonkeyLibText} to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param text the {@link MonkeyLibText} to send
     */
    static void sendText(final CommandContext<Object> ctx, final MonkeyLibText text) {
        INSTANCE.sendTextImpl(ctx, text);
    }

    /**
     * Checks whether the provided command source is executed by a player.
     *
     * @param source the current-platform-specific command source.
     * @return whether the provided command source is executed by a player.
     */
    static boolean executedByPlayer(final Object source) {
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
    static boolean isAdmin(final Object source) {
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
    static boolean isOp(final Object source) {
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
    static boolean isHost(final Object source) {
        return INSTANCE.isHostImpl(source);
    }


    @Internal LiteralArgumentBuilder<?> literalImpl(final String name);
    @Internal <T> RequiredArgumentBuilder<?, T> argumentImpl(final String name, final ArgumentType<T> type);
    @Internal void sendMessageImpl(final CommandContext<Object> ctx, final String message);
    @Internal void sendErrorImpl(final CommandContext<Object> ctx, final String message);
    @Internal void sendTextImpl(final CommandContext<Object> ctx, final MonkeyLibText text);
    @Internal boolean executedByPlayerImpl(final Object source);
    @Internal boolean isOpImpl(final Object source);
    @Internal boolean isHostImpl(final Object source);
}
