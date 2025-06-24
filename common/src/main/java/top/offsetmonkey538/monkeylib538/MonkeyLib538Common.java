package top.offsetmonkey538.monkeylib538;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.monkeylib538.command.MonkeyLib538Command;
import top.offsetmonkey538.monkeylib538.utils.IdentifierUtils;

import java.util.ServiceLoader;

public final class MonkeyLib538Common implements ModInitializer {
    public static final String MOD_ID = "monkeylib538";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(MonkeyLib538Command::register);
    }

    public static Identifier id(String path) {
        return IdentifierUtils.INSTANCE.of(MOD_ID, path);
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }
}
