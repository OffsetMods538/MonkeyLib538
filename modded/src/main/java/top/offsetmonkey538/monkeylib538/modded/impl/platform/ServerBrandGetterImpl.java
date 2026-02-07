package top.offsetmonkey538.monkeylib538.modded.impl.platform;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;

import static top.offsetmonkey538.monkeylib538.modded.api.server.ServerProvider.getServer;

public final class ServerBrandGetterImpl implements LoaderUtil.ServerBrandGetter {
    @Override
    public @Nullable String getBrand() {
        return getServer() == null ? null : getServer().getServerModName();
    }
}
