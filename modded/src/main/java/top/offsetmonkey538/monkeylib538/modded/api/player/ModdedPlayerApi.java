package top.offsetmonkey538.monkeylib538.modded.api.player;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ModdedPlayerApi {
    ModdedPlayerApi INSTANCE = load(ModdedPlayerApi.class);

    static boolean isPlayerOp(final PlayerList playerManager, final Player player) {
        return INSTANCE.isPlayerOpImpl(playerManager, player);
    }
    static boolean isPlayerHost(final MinecraftServer server, final Player player) {
        return INSTANCE.isPlayerHostImpl(server, player);
    }


    // <=1.21.8 use PlayerList.isOp with GameProfile, >=1.21.9 use it with NameAndId
    boolean isPlayerOpImpl(final PlayerList playerManager, final Player player);
    // <=1.21.8 use PlayerList.isSingleplayerOwner with GameProfile, >=1.21.9 use it with NameAndId
    boolean isPlayerHostImpl(final MinecraftServer server, final Player player);
}
