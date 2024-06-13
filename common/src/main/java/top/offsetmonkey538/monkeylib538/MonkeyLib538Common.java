package top.offsetmonkey538.monkeylib538;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.offsetmonkey538.monkeylib538.utils.IdentifierUtils;

import java.util.ServiceLoader;

public final class MonkeyLib538Common {
    private MonkeyLib538Common() {

    }

    public static final String MOD_ID = "monkeylib538";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static Identifier id(String path) {
        return IdentifierUtils.INSTANCE.of(MOD_ID, path);
    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }
}
