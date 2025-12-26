package top.offsetmonkey538.monkeylib538.neoforge.impl.platform;

import net.minecraft.client.ClientBrandRetriever;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

public final class ClientBrandGetterImpl implements PlatformUtil.ClientBrandGetter {
    @Override
    public String get() {
        return ClientBrandRetriever.getClientModName();
    }
}
