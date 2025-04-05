package testmod.config;

import top.offsetmonkey538.monkeylib538.config.ConfigManager;

import static testmod.Testmod.*;

public class ConfigTestmod {

    public void onInitialize() {
        final ModConfig config = ConfigManager.init(new ModConfig(), LOGGER::error);

        LOGGER.info("hello: " + config.hello);
        //LOGGER.info("Number: " + config.Number);
    }
}
