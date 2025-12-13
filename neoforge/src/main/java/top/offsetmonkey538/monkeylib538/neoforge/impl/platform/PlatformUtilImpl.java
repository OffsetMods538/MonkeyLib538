package top.offsetmonkey538.monkeylib538.neoforge.impl.platform;

import net.neoforged.fml.loading.FMLPaths;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import java.nio.file.Path;

public final class PlatformUtilImpl implements PlatformUtil {
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
}
