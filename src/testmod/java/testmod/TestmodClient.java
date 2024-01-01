package testmod;

import net.fabricmc.api.ClientModInitializer;
import testmod.config.ConfigTestmod;

public class TestmodClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new ConfigTestmod().onInitializeClient();
    }
}
