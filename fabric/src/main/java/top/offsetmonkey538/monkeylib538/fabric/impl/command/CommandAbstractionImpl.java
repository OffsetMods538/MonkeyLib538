package top.offsetmonkey538.monkeylib538.fabric.impl.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.command.FabricCommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.fabric.api.player.FabricPlayerApi;
import top.offsetmonkey538.monkeylib538.fabric.impl.text.MonkeyLibTextImpl;

import static top.offsetmonkey538.monkeylib538.fabric.api.command.FabricCommandAbstractionApi.get;

public final class CommandAbstractionImpl implements FabricCommandAbstractionApi {

    @Override
    public @NotNull LiteralArgumentBuilder<?> literalImpl(@NotNull String name) {
        return LiteralArgumentBuilder.<ServerCommandSource>literal(name);
    }

    @Override
    public @NotNull <T> RequiredArgumentBuilder<?, T> argumentImpl(@NotNull String name, @NotNull ArgumentType<T> type) {
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

    @Override
    public boolean executedByPlayerImpl(@NotNull Object source) {
        return get(source).isExecutedByPlayer();
    }

    @Override
    public boolean isOpImpl(@NotNull Object source) {
        return get(source).hasPermissionLevel(get(source).getServer().getOpPermissionLevel());
    }

    @Override
    public boolean isHostImpl(@NotNull Object source) {
        // Using instanceof as a null check and variable assign
        return get(source).getPlayer() instanceof PlayerEntity player && FabricPlayerApi.isPlayerHost(get(source).getServer(), player);
    }

    @Override
    public @NotNull ServerCommandSource getImpl(@NotNull CommandContext<Object> ctx) {
        return getImpl(ctx.getSource());
    }

    @Override
    public @NotNull ServerCommandSource getImpl(@NotNull Object commandSource) {
        if (commandSource instanceof ServerCommandSource serverCommandSource) return serverCommandSource;
        throw new IllegalStateException("Expected command source to be of type '%s', got '%s' instead. Something is very very wrong if you're seeing this error :concern:".formatted(ServerCommandSource.class, commandSource.getClass()));
    }
}
