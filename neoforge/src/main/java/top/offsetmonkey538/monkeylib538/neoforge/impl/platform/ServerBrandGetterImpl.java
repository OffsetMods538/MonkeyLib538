package top.offsetmonkey538.monkeylib538.neoforge.impl.platform;

import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import static top.offsetmonkey538.monkeylib538.neoforge.MonkeyLib538Initializer.getServer;

public final class ServerBrandGetterImpl implements PlatformUtil.ServerBrandGetter {
    @Override
    public String get() {
        return getServer() == null ? null : getServer().getServerModName();
    }
}
