package testmod.config;

import net.minecraft.client.gui.screen.Screen;
import testmod.Testmod;
import top.offsetmonkey538.monkeylib538.config.ConfigScreen;

import static testmod.config.ConfigTestmod.config;

public class ModConfigScreen extends ConfigScreen<ModConfig> {
    public ModConfigScreen(Screen parent) {
        super(parent, Testmod.LOGGER::error);
    }

    @Override
    protected void resetConfig() {
        config = new ModConfig();
    }

    @Override
    protected ModConfig getConfig() {
        return config;
    }
}
