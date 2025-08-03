package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
    static void sendMessage(@NotNull final CommandContext<Object> ctx, @NotNull final String message, @Nullable final Object... args) {
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
    static void sendError(@NotNull final CommandContext<Object> ctx, @NotNull final String message, @Nullable final Object... args) {
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
     * Implementation for {@link #literal(String)}, returns a {@link LiteralArgumentBuilder} for the current-platform-specific command source.
     *
     * @param name the name of the {@link LiteralArgumentBuilder}
     * @return a {@link LiteralArgumentBuilder} for the current-platform-specific command source.
     */
    @ApiStatus.Internal
    @NotNull LiteralArgumentBuilder<?> literalImpl(final String name);
    /**
     * Implementation for {@link #argument(String, ArgumentType)}, returns a {@link RequiredArgumentBuilder} for the current-platform-specific command source.
     *
     * @param name the name of the {@link LiteralArgumentBuilder}
     * @param type the {@link ArgumentType} to use.
     * @return a {@link RequiredArgumentBuilder} for the current-platform-specific command source.
     * @param <T> the type for the {@link ArgumentType}
     */
    @ApiStatus.Internal
    @NotNull <T> RequiredArgumentBuilder<?, T> argumentImpl(final String name, final ArgumentType<T> type);

    /**
     * Sends a message to the provided command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the message to send
     */
    @ApiStatus.Internal
    void sendMessageImpl(@NotNull final CommandContext<Object> ctx, @NotNull final String message);/**
     * Sends an error message to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param message the error message to send
     */
    @ApiStatus.Internal
    void sendErrorImpl(@NotNull final CommandContext<Object> ctx, @NotNull final String message);/**
     * Sends a {@link MonkeyLibText} to the command source.
     *
     * @param ctx the {@link CommandContext} to get the current-platform-specific command source from.
     * @param text the {@link MonkeyLibText} to send
     */
    @ApiStatus.Internal
    void sendTextImpl(@NotNull final CommandContext<Object> ctx, @NotNull final MonkeyLibText text);
}
