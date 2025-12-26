package top.offsetmonkey538.monkeylib538.neoforge.impl.platform;

import net.minecraft.client.ClientBrandRetriever;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import static top.offsetmonkey538.monkeylib538.neoforge.MonkeyLib538Initializer.getServer;

public final class ClientBrandGetterImpl implements PlatformUtil.ClientBrandGetter {
    @Override
    public String get() {
        return ClientBrandRetriever.getClientModName();
    }
}
