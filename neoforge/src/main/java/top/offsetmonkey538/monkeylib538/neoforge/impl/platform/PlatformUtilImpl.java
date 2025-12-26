package top.offsetmonkey538.monkeylib538.neoforge.impl.platform;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.neoforge.api.text.NeoforgeMonkeyLibText;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class PlatformUtilImpl implements PlatformUtil {
    @Override
    public String getMinecraftVersionImpl() {
        return FMLLoader.versionInfo().mcVersion();
    }

    @Override
    public Path getConfigDirImpl() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public Path getModsDirImpl() {
        return FMLPaths.MODSDIR.get();
    }

    @Override
    public Path getGameDirImpl() {
        return FMLPaths.GAMEDIR.get();
    }

    @Override
    public boolean isDevelopmentEnvironmentImpl() {
        return !FMLEnvironment.production;
    }

    @Override
    public boolean isDedicatedServerImpl() {
        return FMLEnvironment.dist == Dist.DEDICATED_SERVER;
    }

    @Override
    public void sendMessagesToAdminsOnJoinImpl(Supplier<MonkeyLibText[]> messageSupplier) {
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, playerLoggedInEvent -> {
            final Player player = playerLoggedInEvent.getEntity();
            final MinecraftServer server = player.getServer();
            if (server == null || !(player instanceof ServerPlayer serverPlayer)) return; // Don't send messages from logical client side. Still should be sending from integrated server though afaik

            if (!server.getPlayerList().isOp(player.getGameProfile()) && !server.isSingleplayerOwner(player.getGameProfile())) return;

            for (MonkeyLibText text : messageSupplier.get()) serverPlayer.sendSystemMessage(NeoforgeMonkeyLibText.of(text).getText(), false);
        });
    }
}
