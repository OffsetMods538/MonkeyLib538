package top.offsetmonkey538.monkeylib538.neoforge.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi;

public interface NeoforgeCommandAbstractionApi extends CommandAbstractionApi {

    static @NotNull CommandSourceStack get(@NotNull CommandContext<Object> ctx) {
        return ((NeoforgeCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static @NotNull CommandSourceStack get(@NotNull Object commandSource) {
        return ((NeoforgeCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    @NotNull CommandSourceStack getImpl(@NotNull CommandContext<Object> ctx);
    @NotNull CommandSourceStack getImpl(@NotNull Object commandSource);
}
