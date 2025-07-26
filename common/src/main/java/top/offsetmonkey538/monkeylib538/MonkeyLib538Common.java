package top.offsetmonkey538.monkeylib538;

import top.offsetmonkey538.monkeylib538.api.log.PlatformLogger;
import top.offsetmonkey538.monkeylib538.api.log.PlatformLoggerProvider;

import java.util.ServiceLoader;

public final class MonkeyLib538Common {
    public static final String MOD_ID = "monkeylib538";
    private static PlatformLogger logger;

    public static <T> T load(Class<T> clazz) {
        System.out.println("loading service for: " + clazz);
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }

    public static PlatformLogger getLogger() {
        if (logger == null) logger = PlatformLoggerProvider.INSTANCE.createLogger(MOD_ID);
        return logger;
    }
}
