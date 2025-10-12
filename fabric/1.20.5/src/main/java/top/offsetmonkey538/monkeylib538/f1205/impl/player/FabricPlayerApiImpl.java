package top.offsetmonkey538.monkeylib538.f1205.impl.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.fabric.api.player.FabricPlayerApi;

public final class FabricPlayerApiImpl implements FabricPlayerApi {
    @Override
    public boolean isPlayerOpImpl(@NotNull PlayerManager playerManager, @NotNull PlayerEntity player) {
        return playerManager.isOperator(player.getGameProfile());
    }
}
