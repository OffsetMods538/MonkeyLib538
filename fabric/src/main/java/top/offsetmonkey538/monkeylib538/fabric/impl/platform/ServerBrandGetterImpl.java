package top.offsetmonkey538.monkeylib538.fabric.impl.platform;

import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import static top.offsetmonkey538.monkeylib538.fabric.MonkeyLib538Initializer.getServer;

public final class ServerBrandGetterImpl implements PlatformUtil.ServerBrandGetter {
    @Override
    public String get() {
        return getServer() == null ? null : getServer().getServerModName();
    }
}
