package top.offsetmonkey538.monkeylib538.api;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.impl.ConfigCommandApiImpl;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;

public interface ConfigCommandApi {
    ConfigCommandApi INSTANCE = new ConfigCommandApiImpl();

    default void register(final @NotNull Identifier id, final @NotNull ConfigHolder<?> configHolder) {
        register(id.toString(), configHolder);
    }

    void register(final @NotNull String id, final @NotNull ConfigHolder<?> configHolder);
}
