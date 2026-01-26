package top.offsetmonkey538.monkeylib538.paper.impl.platform;

import org.bukkit.Bukkit;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;

public final class ServerBrandGetterImpl implements LoaderUtil.ServerBrandGetter {
    @Override
    public String getBrand() {
        return Bukkit.getServer().getName();
    }
}
