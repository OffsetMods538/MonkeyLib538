package top.offsetmonkey538.monkeylib538.neoforge.impl.platform;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.VersionInfo;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;
import top.offsetmonkey538.monkeylib538.modded.api.player.ModdedPlayerApi;
import top.offsetmonkey538.monkeylib538.neoforge.MonkeyLib538Initializer;

import java.nio.file.Path;
import java.util.function.Supplier;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public final class LoaderUtilImpl implements LoaderUtil {
    @Override
    public String getMinecraftVersionImpl() {
        return VersionSpecific.INSTANCE.getVersionInfo().mcVersion();
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
    public boolean isDevelopmentEnvironmentImpl() {
        return !VersionSpecific.INSTANCE.isProduction();
    }

    @Override
    public boolean isDedicatedServerImpl() {
        return VersionSpecific.INSTANCE.getDist() == Dist.DEDICATED_SERVER;
    }

    @Override
    public boolean isEpollEnabledImpl() {
        if (!isDedicatedServerImpl()) return false;
        if (MonkeyLib538Initializer.getServer() == null) throw new IllegalStateException("Tried calling 'isEpollEnabledImpl' before server STARTING event was invoked!");
        return MonkeyLib538Initializer.getServer().getProperties().useNativeTransport;
    }

    @Override
    public int getVanillaServerPortImpl() {
        if (!isDedicatedServerImpl()) throw new IllegalStateException("Tried calling 'getVanillaServerPortImpl' when game isn't a dedicated server!");
        if (MonkeyLib538Initializer.getServer() == null) throw new IllegalStateException("Tried calling 'getVanillaServerPortImpl' before server STARTING event was invoked!");
        return MonkeyLib538Initializer.getServer().getServerPort();
    }

    @Override
    public void sendMessagesToAdminsOnJoinImpl(Supplier<Component[]> messageSupplier) {
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, playerLoggedInEvent -> {
            final Player player = playerLoggedInEvent.getEntity();
            final MinecraftServer server = player.level().getServer();
            if (server == null || !(player instanceof ServerPlayer serverPlayer)) return; // Don't send messages from logical client side. Still should be sending from integrated server though afaik

            if (!ModdedPlayerApi.isPlayerOp(server.getPlayerList(), player) && !ModdedPlayerApi.isPlayerHost(server, player)) return;

            for (Component text : messageSupplier.get()) ((Audience) serverPlayer).sendMessage(text);
        });
    }

    public interface VersionSpecific {
        VersionSpecific INSTANCE = load(VersionSpecific.class);

        VersionInfo getVersionInfo();
        boolean isProduction();
        Dist getDist();
    }
}
