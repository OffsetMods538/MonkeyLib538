package testmod.config;

import top.offsetmonkey538.monkeylib538.config.ConfigManager;
import top.offsetmonkey538.monkeylib538.config.MonkeyLib538ModMenu;

import static testmod.Testmod.*;

public class ConfigTestmod {
    public static ModConfig config;

    public void onInitialize() {
        config = ConfigManager.init(new ModConfig(), LOGGER::error);

        LOGGER.info("hello: " + config.hello);
        LOGGER.info("Number: " + config.Number);
    }

    public void onInitializeClient() {
        MonkeyLib538ModMenu.registerConfigScreen(MOD_ID, ModConfigScreen::new);
    }
}
