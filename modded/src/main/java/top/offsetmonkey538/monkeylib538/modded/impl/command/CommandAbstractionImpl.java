package top.offsetmonkey538.monkeylib538.modded.impl.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.modded.api.player.ModdedPlayerApi;
import top.offsetmonkey538.monkeylib538.modded.impl.text.MonkeyLibTextImpl;

import static top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi.get;

public final class CommandAbstractionImpl implements ModdedCommandAbstractionApi {

    @Override
    public LiteralArgumentBuilder<?> literalImpl(String name) {
        return LiteralArgumentBuilder.<ServerCommandSource>literal(name);
    }

    @Override
    public <T> RequiredArgumentBuilder<?, T> argumentImpl(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.<ServerCommandSource, T>argument(name, type);
    }

    @Override
    public void sendMessageImpl(CommandContext<Object> ctx, String message) {
        get(ctx).sendFeedback(() -> Text.of(message), true);
    }

    @Override
    public void sendErrorImpl(CommandContext<Object> ctx, String message) {
        get(ctx).sendError(Text.of(message));
    }

    @Override
    public void sendTextImpl(CommandContext<Object> ctx, MonkeyLibText text) {
        get(ctx).sendFeedback(() -> ((MonkeyLibTextImpl) text).getText(), true);
    }

    @Override
    public boolean executedByPlayerImpl(Object source) {
        return get(source).isExecutedByPlayer();
    }

    @Override
    public boolean isOpImpl(Object source) {
        return get(source).hasPermissionLevel(get(source).getServer().getOpPermissionLevel());
    }

    @Override
    public boolean isHostImpl(Object source) {
        // Using instanceof as a null check and variable assign
        return get(source).getPlayer() instanceof PlayerEntity player && ModdedPlayerApi.isPlayerHost(get(source).getServer(), player);
    }

    @Override
    public ServerCommandSource getImpl(CommandContext<Object> ctx) {
        return getImpl(ctx.getSource());
    }

    @Override
    public ServerCommandSource getImpl(Object commandSource) {
        if (commandSource instanceof ServerCommandSource serverCommandSource) return serverCommandSource;
        throw new IllegalStateException("Expected command source to be of type '%s', got '%s' instead. Something is very very wrong if you're seeing this error :concern:".formatted(ServerCommandSource.class, commandSource.getClass()));
    }
}
