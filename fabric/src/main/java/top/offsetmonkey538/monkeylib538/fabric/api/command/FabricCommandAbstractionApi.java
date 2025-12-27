package top.offsetmonkey538.monkeylib538.fabric.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi;

public interface FabricCommandAbstractionApi extends CommandAbstractionApi {
    static @NotNull ServerCommandSource get(@NotNull CommandContext<Object> ctx) {
        return ((FabricCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static @NotNull ServerCommandSource get(@NotNull Object commandSource) {
        return ((FabricCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    @NotNull ServerCommandSource getImpl(@NotNull CommandContext<Object> ctx);
    @NotNull ServerCommandSource getImpl(@NotNull Object commandSource);
}
