package top.offsetmonkey538.monkeylib538.paper.impl.platform;

import org.bukkit.Bukkit;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;

public final class ServerBrandGetterImpl implements LoaderUtil.ServerBrandGetter {
    @Override
    public @Nullable String getBrand() {
        return Bukkit.getServer().getName();
    }
}
