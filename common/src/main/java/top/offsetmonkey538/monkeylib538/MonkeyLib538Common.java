package top.offsetmonkey538.monkeylib538;

import top.offsetmonkey538.monkeylib538.api.log.MonkeyLibLogger;

import java.util.ServiceLoader;

public final class MonkeyLib538Common {
    public static final String MOD_ID = "monkeylib538";
    private static MonkeyLibLogger logger;

    public static <T> T load(Class<T> clazz) {
        System.out.println("loading service for: " + clazz);
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }

    public static MonkeyLibLogger getLogger() {
        if (logger == null) logger = MonkeyLibLogger.create(MOD_ID);
        return logger;
    }
}
