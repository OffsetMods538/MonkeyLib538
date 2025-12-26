package top.offsetmonkey538.monkeylib538.fabric.impl.platform;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.SystemProperties;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.fabric.api.player.FabricPlayerApi;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibText;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;

public final class PlatformUtilImpl implements PlatformUtil {
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
        return directory == null ? getGameDirImpl().resolve("mods") : Paths.get(directory);
    }

    @Override
    public Path getGameDirImpl() {
        return FabricLoader.getInstance().getGameDir();
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
    public void sendMessagesToAdminsOnJoinImpl(Supplier<MonkeyLibText[]> messageSupplier) {
        ServerPlayConnectionEvents.JOIN.register(((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            if (!FabricPlayerApi.isPlayerOp(minecraftServer.getPlayerManager(), serverPlayNetworkHandler.player) && !FabricPlayerApi.isPlayerHost(minecraftServer, serverPlayNetworkHandler.player)) return;

            // Have to use sendMessageToClient cause sendMessage didn't exist in some older versions
            for (MonkeyLibText text : messageSupplier.get()) serverPlayNetworkHandler.player.sendMessageToClient(FabricMonkeyLibText.of(text).getText(), false);
        }));
    }
}
