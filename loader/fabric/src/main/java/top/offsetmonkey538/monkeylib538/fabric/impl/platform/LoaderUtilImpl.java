package top.offsetmonkey538.monkeylib538.fabric.impl.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.SystemProperties;
import net.kyori.adventure.text.Component;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;
import top.offsetmonkey538.monkeylib538.fabric.MonkeyLib538Initializer;
import top.offsetmonkey538.monkeylib538.modded.api.player.ModdedPlayerApi;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public final class LoaderUtilImpl implements LoaderUtil {
    @Override
    public String getMinecraftVersionImpl() {
        return FabricLoader.getInstance().getRawGameVersion();
    }

    @Override
    public Path getConfigDirImpl() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public Path getModsDirImpl() {
        final String directory = System.getProperty(SystemProperties.MODS_FOLDER);
        return directory == null ? FabricLoader.getInstance().getGameDir().resolve("mods") : Paths.get(directory);
    }

    @Override
    public boolean isDevelopmentEnvironmentImpl() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public boolean isDedicatedServerImpl() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public boolean isEpollEnabledImpl() {
        if (!isDedicatedServerImpl()) return false;
        if (MonkeyLib538Initializer.getServer() == null) throw new IllegalStateException("Tried calling 'isEpollEnabledImpl' before server STARTING event was invoked!");
        return MonkeyLib538Initializer.getServer().getProperties().useNativeTransport;
    }

    @Override
    public void sendMessagesToAdminsOnJoinImpl(Supplier<Component[]> messageSupplier) {
        ServerPlayConnectionEvents.JOIN.register(((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            if (!ModdedPlayerApi.isPlayerOp(minecraftServer.getPlayerList(), serverPlayNetworkHandler.player) && !ModdedPlayerApi.isPlayerHost(minecraftServer, serverPlayNetworkHandler.player)) return;

            for (Component text : messageSupplier.get()) serverPlayNetworkHandler.player.sendMessage(text);
        }));
    }
}
