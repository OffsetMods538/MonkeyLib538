package top.offsetmonkey538.monkeylib538.neoforge.impl.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.command.NeoforgeCommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.neoforge.impl.text.MonkeyLibTextImpl;

import static top.offsetmonkey538.monkeylib538.neoforge.api.command.NeoforgeCommandAbstractionApi.get;

public final class CommandAbstractionImpl implements NeoforgeCommandAbstractionApi {

    @Override
    public @NotNull LiteralArgumentBuilder<?> literalImpl(@NotNull String name) {
        return LiteralArgumentBuilder.<CommandSourceStack>literal(name);
    }

    @Override
    public @NotNull <T> RequiredArgumentBuilder<?, T> argumentImpl(@NotNull String name, @NotNull ArgumentType<T> type) {
        return RequiredArgumentBuilder.<CommandSourceStack, T>argument(name, type);
    }

    @Override
    public void sendMessageImpl(@NotNull CommandContext<Object> ctx, @NotNull String message) {
        get(ctx).sendSuccess(() -> Component.literal(message), true);
    }

    @Override
    public void sendErrorImpl(@NotNull CommandContext<Object> ctx, @NotNull String message) {
        get(ctx).sendFailure(Component.literal(message));
    }

    @Override
    public void sendTextImpl(@NotNull CommandContext<Object> ctx, @NotNull MonkeyLibText text) {
        get(ctx).sendSuccess(() -> ((MonkeyLibTextImpl) text).getText(), true);
    }

    @Override
    public boolean executedByPlayerImpl(@NotNull Object source) {
        return get(source).isPlayer();
    }

    @Override
    public boolean isOpImpl(@NotNull Object source) {
        return get(source).hasPermission(get(source).getServer().getOperatorUserPermissionLevel());
    }

    @Override
    public @NotNull CommandSourceStack getImpl(@NotNull CommandContext<Object> ctx) {
        return getImpl(ctx.getSource());
    }

    @Override
    public @NotNull CommandSourceStack getImpl(@NotNull Object commandSource) {
        if (commandSource instanceof CommandSourceStack serverCommandSource) return serverCommandSource;
        throw new IllegalStateException("Expected command source to be of type '%s', got '%s' instead. Something is very very wrong if you're seeing this error :concern:".formatted(CommandSourceStack.class, commandSource.getClass()));
    }
}
