package top.offsetmonkey538.monkeylib538.f1205.impl.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import top.offsetmonkey538.monkeylib538.fabric.api.player.FabricPlayerApi;

public final class FabricPlayerApiImpl implements FabricPlayerApi {
    @Override
    public boolean isPlayerOpImpl(PlayerManager playerManager, PlayerEntity player) {
        return playerManager.isOperator(player.getGameProfile());
    }

    @Override
    public boolean isPlayerHostImpl(MinecraftServer server, PlayerEntity player) {
        return server.isHost(player.getGameProfile());
    }
}
