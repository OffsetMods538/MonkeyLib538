package top.offsetmonkey538.monkeylib538.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.Map;

public class MonkeyLib538ModMenu implements ModMenuApi {
    private static final Map<String, ConfigScreenFactory<?>> CONFIG_SCREENS = new HashMap<>();

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return CONFIG_SCREENS;
    }

    public static void registerConfigScreen(String modId, ConfigScreenFactory<?> configScreenFactory) {
        CONFIG_SCREENS.put(modId, configScreenFactory);
    }
}
