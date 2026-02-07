package top.offsetmonkey538.monkeylib538.common.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;
import top.offsetmonkey538.offsetutils538.api.text.ArgReplacer;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;
import static top.offsetmonkey538.offsetutils538.api.text.ArgReplacer.replaceArgs;

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
     * @param message the message to send, will be formatted using {@link ArgReplacer#replaceArgs(String, Object)}
     * @param arg the arg for replacing in the message
     */
    static void sendMessage(final CommandContext<Object> ctx, final String message, final Object arg) {
        sendMessage(ctx, replaceArgs(message, arg));
    }

    /**
     * Sends a message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send, will be formatted using {@link ArgReplacer#replaceArgs(String, Object, Object)}
     * @param arg1 the first arg for replacing in the message
     * @param arg2 the second arg for replacing in the message
     */
    static void sendMessage(final CommandContext<Object> ctx, final String message, @Nullable final Object arg1, @Nullable final Object arg2) {
        sendMessage(ctx, replaceArgs(message, arg1, arg2));
    }

    /**
     * Sends a message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send, will be formatted using {@link ArgReplacer#replaceArgs(String, Object...)}
     * @param args the args for replacing in the message
     */
    static void sendMessage(final CommandContext<Object> ctx, final String message, @Nullable final Object... args) {
        sendMessage(ctx, replaceArgs(message, args));
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
     * @param message the error message to send, will be formatted using {@link ArgReplacer#replaceArgs(String, Object...)}
     * @param arg the args for replacing in the error message
     */
    static void sendError(final CommandContext<Object> ctx, final String message, @Nullable final Object arg) {
        sendError(ctx, replaceArgs(message, arg));
    }

    /**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send, will be formatted using {@link ArgReplacer#replaceArgs(String, Object...)}
     * @param arg1 the first arg for replacing in the error message
     * @param arg2 the second arg for replacing in the error message
     */
    static void sendError(final CommandContext<Object> ctx, final String message, @Nullable final Object arg1, @Nullable final Object arg2) {
        sendError(ctx, replaceArgs(message, arg1, arg2));
    }

    /**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send, will be formatted using {@link ArgReplacer#replaceArgs(String, Object...)}
     * @param args the args for replacing in the error message
     */
    static void sendError(final CommandContext<Object> ctx, final String message, @Nullable final Object... args) {
        sendError(ctx, replaceArgs(message, args));
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
     * Sends a {@link Component} to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param text the {@link Component} to send
     */
    static void sendText(final CommandContext<Object> ctx, final Component text) {
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
    @Internal void sendTextImpl(final CommandContext<Object> ctx, final Component text);
    @Internal boolean executedByPlayerImpl(final Object source);
    @Internal boolean isOpImpl(final Object source);
    @Internal boolean isHostImpl(final Object source);
}
