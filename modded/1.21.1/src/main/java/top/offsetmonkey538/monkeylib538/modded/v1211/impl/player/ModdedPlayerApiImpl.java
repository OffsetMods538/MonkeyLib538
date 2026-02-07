package top.offsetmonkey538.monkeylib538.modded.v1211.impl.player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import top.offsetmonkey538.monkeylib538.modded.api.player.ModdedPlayerApi;

public final class ModdedPlayerApiImpl implements ModdedPlayerApi {
    @Override
    public boolean isPlayerOpImpl(PlayerList playerManager, Player player) {
        return playerManager.isOp(player.getGameProfile());
    }

    @Override
    public boolean isPlayerHostImpl(MinecraftServer server, Player player) {
        return server.isSingleplayerOwner(player.getGameProfile());
    }
}
