package top.offsetmonkey538.monkeylib538.modded.v1205.impl.command;

import net.minecraft.server.MinecraftServer;
import top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi;

public final class OperatorPermissionLevelProviderImpl implements ModdedCommandAbstractionApi.OperatorPermissionLevelProvider {
    @Override
    public int getPermissionLevelImpl(MinecraftServer server) {
        return server.getOperatorUserPermissionLevel();
    }
}
