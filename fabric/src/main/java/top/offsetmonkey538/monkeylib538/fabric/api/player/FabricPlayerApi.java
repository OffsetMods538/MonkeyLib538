package top.offsetmonkey538.monkeylib538.fabric.api.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface FabricPlayerApi {
    FabricPlayerApi INSTANCE = load(FabricPlayerApi.class);

    static boolean isPlayerOp(final PlayerManager playerManager, final PlayerEntity player) {
        return INSTANCE.isPlayerOpImpl(playerManager, player);
    }
    static boolean isPlayerHost(final MinecraftServer server, final PlayerEntity player) {
        return INSTANCE.isPlayerHostImpl(server, player);
    }


    boolean isPlayerOpImpl(final PlayerManager playerManager, final PlayerEntity player);
    boolean isPlayerHostImpl(final MinecraftServer server, final PlayerEntity player);
}
