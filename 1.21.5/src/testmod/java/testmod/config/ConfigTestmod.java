package testmod.config;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import top.offsetmonkey538.monkeylib538.api.ConfigCommandApi;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;

import static net.minecraft.server.command.CommandManager.literal;
import static testmod.Testmod.*;

public class ConfigTestmod {

    public void onInitialize() {
        final ConfigHolder<ModConfig> config = ConfigManager.INSTANCE.init(ConfigHolder.create(ModConfig::new, LOGGER::error));
        final ConfigHolder<ModConfig2> config2 = ConfigManager.INSTANCE.init(ConfigHolder.create(ModConfig2::new, LOGGER::error));

        CommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess, registrationEnvironment) -> {
            dispatcher.register(literal(MOD_ID)
                            .then(ConfigCommandApi.INSTANCE.createConfigCommand("main", config))
                            .then(ConfigCommandApi.INSTANCE.createConfigCommand("second", config2))
            );
        });

        LOGGER.info("hello: {}", config.get().hello);
        //LOGGER.info("Number: " + config.Number);
    }
}
