package top.offsetmonkey538.monkeylib538.fabric.api.player;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface FabricPlayerApi {
    @ApiStatus.Internal
    FabricPlayerApi INSTANCE = load(FabricPlayerApi.class);

    static boolean isPlayerOp(final @NotNull PlayerManager playerManager, final @NotNull PlayerEntity player) {
        return INSTANCE.isPlayerOpImpl(playerManager, player);
    }
    static boolean isPlayerHost(final @NotNull MinecraftServer server, final @NotNull PlayerEntity player) {
        return INSTANCE.isPlayerHostImpl(server, player);
    }


    boolean isPlayerOpImpl(final @NotNull PlayerManager playerManager, final @NotNull PlayerEntity player);
    boolean isPlayerHostImpl(final @NotNull MinecraftServer server, final @NotNull PlayerEntity player);
}
