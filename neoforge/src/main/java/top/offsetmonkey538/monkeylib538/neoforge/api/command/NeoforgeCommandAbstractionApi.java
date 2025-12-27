package top.offsetmonkey538.monkeylib538.neoforge.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import top.offsetmonkey538.monkeylib538.api.command.CommandAbstractionApi;

public interface NeoforgeCommandAbstractionApi extends CommandAbstractionApi {

    static CommandSourceStack get(CommandContext<Object> ctx) {
        return ((NeoforgeCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static CommandSourceStack get(Object commandSource) {
        return ((NeoforgeCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    CommandSourceStack getImpl(CommandContext<Object> ctx);
    CommandSourceStack getImpl(Object commandSource);
}
