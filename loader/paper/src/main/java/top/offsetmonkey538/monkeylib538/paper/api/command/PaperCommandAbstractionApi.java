package top.offsetmonkey538.monkeylib538.paper.api.command;

import com.mojang.brigadier.context.CommandContext;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

public interface PaperCommandAbstractionApi extends CommandAbstractionApi {
    static CommandSourceStack get(CommandContext<Object> ctx) {
        return ((PaperCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static CommandSourceStack get(Object commandSource) {
        return ((PaperCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    @Internal
    CommandSourceStack getImpl(CommandContext<Object> ctx);
    @Internal CommandSourceStack getImpl(Object commandSource);
}
