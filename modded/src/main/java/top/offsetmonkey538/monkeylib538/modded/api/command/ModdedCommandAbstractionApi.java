package top.offsetmonkey538.monkeylib538.modded.api.command;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import top.offsetmonkey538.monkeylib538.common.api.command.CommandAbstractionApi;

import java.util.function.IntFunction;
import java.util.function.IntSupplier;

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
        return get(source).hasPermission(OperatorPermissionLevelProvider.getPermissionLevel(get(source).getServer()));
    }

    CommandSourceStack getImpl(CommandContext<Object> ctx);
    CommandSourceStack getImpl(Object commandSource);
    
    interface OperatorPermissionLevelProvider {
        OperatorPermissionLevelProvider INSTANCE = load(OperatorPermissionLevelProvider.class);

        static int getPermissionLevel(MinecraftServer server) {
            return INSTANCE.getPermissionLevelImpl(server);
        }

        int getPermissionLevelImpl(MinecraftServer server);
    }
}
