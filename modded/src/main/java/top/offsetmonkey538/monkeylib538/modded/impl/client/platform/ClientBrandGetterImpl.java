package top.offsetmonkey538.monkeylib538.modded.impl.client.platform;

import net.minecraft.client.ClientBrandRetriever;
import top.offsetmonkey538.monkeylib538.api.platform.LoaderUtil;

public final class ClientBrandGetterImpl implements LoaderUtil.ClientBrandGetter {
    @Override
    public String getBrand() {
        return ClientBrandRetriever.getClientModName();
    }
}
