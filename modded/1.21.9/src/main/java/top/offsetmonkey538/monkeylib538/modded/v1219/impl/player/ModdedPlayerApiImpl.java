package top.offsetmonkey538.monkeylib538.modded.v1219.impl.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import top.offsetmonkey538.monkeylib538.modded.api.player.ModdedPlayerApi;

public final class ModdedPlayerApiImpl implements ModdedPlayerApi {
    @Override
    public boolean isPlayerOpImpl(PlayerManager playerManager, PlayerEntity player) {
        return playerManager.isOperator(player.getPlayerConfigEntry());
    }

    @Override
    public boolean isPlayerHostImpl(MinecraftServer server, PlayerEntity player) {
        return server.isHost(player.getPlayerConfigEntry());
    }
}
