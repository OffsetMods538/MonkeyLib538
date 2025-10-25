package top.offsetmonkey538.monkeylib538.fabric.impl.platform;

import net.fabricmc.loader.api.FabricLoader;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import java.nio.file.Path;

public final class PlatformUtilImpl implements PlatformUtil {

    @Override
    public Path getConfigDirImpl() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
