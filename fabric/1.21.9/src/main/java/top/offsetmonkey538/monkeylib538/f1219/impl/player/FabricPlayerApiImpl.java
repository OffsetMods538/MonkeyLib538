package top.offsetmonkey538.monkeylib538.f1219.impl.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.fabric.api.player.FabricPlayerApi;

public final class FabricPlayerApiImpl implements FabricPlayerApi {
    @Override
    public boolean isPlayerOpImpl(@NotNull PlayerManager playerManager, @NotNull PlayerEntity player) {
        return playerManager.isOperator(player.getPlayerConfigEntry());
    }

    @Override
    public boolean isPlayerHostImpl(@NotNull MinecraftServer server, @NotNull PlayerEntity player) {
        return server.isHost(player.getPlayerConfigEntry());
    }
}
