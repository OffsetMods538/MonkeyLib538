package top.offsetmonkey538.monkeylib538.fabric.impl.platform;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.util.SystemProperties;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PlatformUtilImpl implements PlatformUtil {
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
}
