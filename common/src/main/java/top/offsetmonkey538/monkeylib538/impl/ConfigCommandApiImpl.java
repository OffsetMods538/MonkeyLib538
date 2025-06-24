package top.offsetmonkey538.monkeylib538.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;
import top.offsetmonkey538.monkeylib538.api.ConfigCommandApi;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfigCommandApiImpl implements ConfigCommandApi {
    private static final Map<String, ConfigHolder<?>> CONFIG_HOLDERS = new HashMap<>();
    private static boolean initialized;

    @Override
    public void register(@NotNull String id, @NotNull ConfigHolder<?> configHolder) {
        if (initialized) throw new IllegalStateException("Config command for config with id '%s' registered after commands have been registered!".formatted(id));
        CONFIG_HOLDERS.put(id, configHolder);
    }

    public @UnmodifiableView Map<String, ConfigHolder<?>> getConfigs() {
        return Collections.unmodifiableMap(CONFIG_HOLDERS);
    }

    public void setInitialized() {
        initialized = true;
    }
}
