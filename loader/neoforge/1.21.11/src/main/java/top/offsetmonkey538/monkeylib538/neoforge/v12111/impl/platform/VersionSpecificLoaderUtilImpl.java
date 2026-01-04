package top.offsetmonkey538.monkeylib538.neoforge.v12111.impl.platform;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.VersionInfo;
import top.offsetmonkey538.monkeylib538.neoforge.impl.platform.LoaderUtilImpl;

public final class VersionSpecificLoaderUtilImpl implements LoaderUtilImpl.VersionSpecific {
    @Override
    public VersionInfo getVersionInfo() {
        return FMLLoader.getCurrent().getVersionInfo();
    }

    @Override
    public boolean isProduction() {
        return FMLEnvironment.isProduction();
    }

    @Override
    public Dist getDist() {
        return FMLEnvironment.getDist();
    }
}
