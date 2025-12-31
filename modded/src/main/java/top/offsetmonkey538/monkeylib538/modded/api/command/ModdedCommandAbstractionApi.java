package top.offsetmonkey538.monkeylib538.modded.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi;

public interface ModdedCommandAbstractionApi extends CommandAbstractionApi {
    static ServerCommandSource get(CommandContext<Object> ctx) {
        return ((ModdedCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static ServerCommandSource get(Object commandSource) {
        return ((ModdedCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    ServerCommandSource getImpl(CommandContext<Object> ctx);
    ServerCommandSource getImpl(Object commandSource);
}
