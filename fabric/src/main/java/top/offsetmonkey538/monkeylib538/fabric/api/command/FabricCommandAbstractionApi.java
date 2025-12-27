package top.offsetmonkey538.monkeylib538.fabric.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi;

public interface FabricCommandAbstractionApi extends CommandAbstractionApi {
    static ServerCommandSource get(CommandContext<Object> ctx) {
        return ((FabricCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static ServerCommandSource get(Object commandSource) {
        return ((FabricCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    ServerCommandSource getImpl(CommandContext<Object> ctx);
    ServerCommandSource getImpl(Object commandSource);
}
