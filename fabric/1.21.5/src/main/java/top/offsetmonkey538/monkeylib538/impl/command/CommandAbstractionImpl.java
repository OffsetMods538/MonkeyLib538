package top.offsetmonkey538.monkeylib538.impl.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.impl.text.MonkeyLibTextImpl;

public class CommandAbstractionImpl implements CommandAbstractionApi {

    @Override
    public @NotNull LiteralArgumentBuilder<?> literalImpl(String name) {
        return LiteralArgumentBuilder.<ServerCommandSource>literal(name);
    }

    @Override
    public @NotNull <T> RequiredArgumentBuilder<?, T> argumentImpl(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.<ServerCommandSource, T>argument(name, type);
    }

    @Override
    public void sendMessageImpl(@NotNull CommandContext<Object> ctx, @NotNull String message) {
        get(ctx).sendFeedback(() -> Text.of(message), true);
    }

    @Override
    public void sendErrorImpl(@NotNull CommandContext<Object> ctx, @NotNull String message) {
        get(ctx).sendError(Text.of(message));
    }

    @Override
    public void sendTextImpl(@NotNull CommandContext<Object> ctx, @NotNull MonkeyLibText text) {
        get(ctx).sendFeedback(() -> ((MonkeyLibTextImpl) text).getText(), true);
    }

    private static @NotNull ServerCommandSource get(@NotNull CommandContext<Object> ctx) {
        final Object commandSource = ctx.getSource();

        if (commandSource instanceof ServerCommandSource serverCommandSource) return serverCommandSource;
        throw new IllegalStateException("Expected command source to be of type '%s', got '%s' instead. Something is very very wrong if you're seeing this error :concern:".formatted(ServerCommandSource.class, commandSource.getClass()));
    }
}
