package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface CommandAbstractionApi {
    CommandAbstractionApi INSTANCE = load(CommandAbstractionApi.class);

    @SuppressWarnings("unchecked")
    static LiteralArgumentBuilder<Object> literal(final String name) {
        return (LiteralArgumentBuilder<Object>) INSTANCE.literalImpl(name);
    }
    @SuppressWarnings("unchecked")
    static <T> RequiredArgumentBuilder<Object, T> argument(final String name, final ArgumentType<T> type) {
        return (RequiredArgumentBuilder<Object, T>) INSTANCE.argumentImpl(name, type);
    }

    static void sendMessage(@NotNull final CommandContext<Object> ctx, @NotNull final String message, @Nullable final Object... args) {
        sendMessage(ctx, message.formatted(args));
    }
    static void sendMessage(@NotNull final CommandContext<Object> ctx, @NotNull final String message) {
        INSTANCE.sendMessageImpl(ctx, message);
    }

    static void sendError(@NotNull final CommandContext<Object> ctx, @NotNull final String message, @Nullable final Object... args) {
        sendError(ctx, message.formatted(args));
    }
    static void sendError(@NotNull final CommandContext<Object> ctx, @NotNull final String message) {
        INSTANCE.sendErrorImpl(ctx, message);
    }

    static void sendText(@NotNull final CommandContext<Object> ctx, @NotNull final MonkeyLibText text) {
        INSTANCE.sendTextImpl(ctx, text);
    }


    @NotNull LiteralArgumentBuilder<?> literalImpl(final String name);
    @NotNull <T> RequiredArgumentBuilder<?, T> argumentImpl(final String name, final ArgumentType<T> type);

    /**
     * Should broadcast to OPs
     *
     * @param message
     */
    void sendMessageImpl(@NotNull final CommandContext<Object> ctx, @NotNull final String message);
    void sendErrorImpl(@NotNull final CommandContext<Object> ctx, @NotNull final String message);
    void sendTextImpl(@NotNull final CommandContext<Object> ctx, @NotNull final MonkeyLibText text);







}
