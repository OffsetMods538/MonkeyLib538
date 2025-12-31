package top.offsetmonkey538.monkeylib538.modded.impl.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.modded.api.player.ModdedPlayerApi;
import top.offsetmonkey538.monkeylib538.modded.impl.text.MonkeyLibTextImpl;

import static top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi.get;

public final class CommandAbstractionImpl implements ModdedCommandAbstractionApi {

    @Override
    public LiteralArgumentBuilder<?> literalImpl(String name) {
        return LiteralArgumentBuilder.<CommandSourceStack>literal(name);
    }

    @Override
    public <T> RequiredArgumentBuilder<?, T> argumentImpl(String name, ArgumentType<T> type) {
        return RequiredArgumentBuilder.<CommandSourceStack, T>argument(name, type);
    }

    @Override
    public void sendMessageImpl(CommandContext<Object> ctx, String message) {
        get(ctx).sendSuccess(() -> Component.nullToEmpty(message), true);
    }

    @Override
    public void sendErrorImpl(CommandContext<Object> ctx, String message) {
        get(ctx).sendFailure(Component.nullToEmpty(message));
    }

    @Override
    public void sendTextImpl(CommandContext<Object> ctx, MonkeyLibText text) {
        get(ctx).sendSuccess(() -> ((MonkeyLibTextImpl) text).getText(), true);
    }

    @Override
    public boolean executedByPlayerImpl(Object source) {
        return get(source).isPlayer();
    }

    @Override
    public boolean isHostImpl(Object source) {
        // Using instanceof as a null check and variable assign
        return get(source).getPlayer() instanceof Player player && ModdedPlayerApi.isPlayerHost(get(source).getServer(), player);
    }

    @Override
    public CommandSourceStack getImpl(CommandContext<Object> ctx) {
        return getImpl(ctx.getSource());
    }

    @Override
    public CommandSourceStack getImpl(Object commandSource) {
        if (commandSource instanceof CommandSourceStack serverCommandSource) return serverCommandSource;
        throw new IllegalStateException("Expected command source to be of type '%s', got '%s' instead. Something is very very wrong if you're seeing this error :concern:".formatted(CommandSourceStack.class, commandSource.getClass()));
    }
}
