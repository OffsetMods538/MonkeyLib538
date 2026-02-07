package top.offsetmonkey538.monkeylib538.modded.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ModdedCommandAbstractionApi extends CommandAbstractionApi {
    static CommandSourceStack get(CommandContext<Object> ctx) {
        return ((ModdedCommandAbstractionApi) INSTANCE).getImpl(ctx);
    }
    static CommandSourceStack get(Object commandSource) {
        return ((ModdedCommandAbstractionApi) INSTANCE).getImpl(commandSource);
    }

    @Override
    default boolean isOpImpl(final Object source) {
        return VersionSpecific.INSTANCE.isOp(get(source));
    }

    @Internal
    CommandSourceStack getImpl(CommandContext<Object> ctx);
    @Internal CommandSourceStack getImpl(Object commandSource);

    @Internal
    interface VersionSpecific {
        VersionSpecific INSTANCE = load(VersionSpecific.class);

        boolean isOp(final CommandSourceStack source);
    }
}
