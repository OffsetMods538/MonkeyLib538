package top.offsetmonkey538.monkeylib538.paper.impl.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibStyle;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.paper.api.command.PaperCommandAbstractionApi;
import top.offsetmonkey538.monkeylib538.paper.api.text.PaperMonkeyLibText;

import static top.offsetmonkey538.monkeylib538.paper.api.command.PaperCommandAbstractionApi.get;

public final class CommandAbstractionImpl implements PaperCommandAbstractionApi {

    @Override
    public LiteralArgumentBuilder<?> literalImpl(String name) {
        return Commands.literal(name);
    }

    @Override
    public <T> RequiredArgumentBuilder<?, T> argumentImpl(String name, ArgumentType<T> type) {
        return Commands.argument(name, type);
    }

    @Override
    public void sendMessageImpl(CommandContext<Object> ctx, String message) {
        get(ctx).getSender().sendMessage(message);
    }

    @Override
    public void sendErrorImpl(CommandContext<Object> ctx, String message) {
        get(ctx).getSender().sendMessage(PaperMonkeyLibText.of(MonkeyLibText.of(message).setStyle(MonkeyLibStyle.empty().withColor(MonkeyLibStyle.Color.RED))).getText());
    }

    @Override
    public void sendTextImpl(CommandContext<Object> ctx, MonkeyLibText text) {
        get(ctx).getSender().sendMessage(PaperMonkeyLibText.of(text).getText());
    }

    @Override
    public boolean executedByPlayerImpl(Object source) {
        return get(source).getExecutor() instanceof Player;
    }

    @Override
    public boolean isOpImpl(Object source) {
        return get(source).getSender().isOp();
    }

    @Override
    public boolean isHostImpl(Object source) {
        return false; // No host on dedicated server
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
