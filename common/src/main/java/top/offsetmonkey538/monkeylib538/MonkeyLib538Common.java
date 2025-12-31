package top.offsetmonkey538.monkeylib538;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.log.MonkeyLibLogger;
import top.offsetmonkey538.monkeylib538.telemetry.TelemetryHandler;

import java.util.ServiceLoader;

public final class MonkeyLib538Common {
    public static final String MOD_ID = "monkeylib538";
    private static @Nullable MonkeyLibLogger logger;

    public static void initialize() {
        TelemetryHandler.initialize();
    }

    public static <T> T load(Class<T> clazz) {
        System.out.println("loading service for: " + clazz);
        return ServiceLoader.load(clazz, MonkeyLib538Common.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }

    // Have to do it this way cause otherwise something wanted recursive loading or smt I think
    public static MonkeyLibLogger getLogger() {
        if (logger == null) logger = MonkeyLibLogger.create(MOD_ID);
        return logger;
    }
}
