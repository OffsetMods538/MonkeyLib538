package testmod.config;


import net.minecraft.util.Identifier;
import top.offsetmonkey538.monkeylib538.api.ConfigCommandApi;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;

import static testmod.Testmod.*;

public class ConfigTestmod {

    public void onInitialize() {
        final ConfigHolder<ModConfig> config = ConfigManager.INSTANCE.init(ConfigHolder.create(ModConfig::new, LOGGER::error));
        final ConfigHolder<ModConfig2> config2 = ConfigManager.INSTANCE.init(ConfigHolder.create(ModConfig2::new, LOGGER::error));

        ConfigCommandApi.INSTANCE.register(MOD_ID, config);
        ConfigCommandApi.INSTANCE.register(Identifier.of(MOD_ID, "2"), config2);

        LOGGER.info("hello: {}", config.get().hello);
        //LOGGER.info("Number: " + config.Number);
    }
}
