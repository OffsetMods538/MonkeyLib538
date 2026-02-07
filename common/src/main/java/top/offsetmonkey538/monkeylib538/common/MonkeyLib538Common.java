package top.offsetmonkey538.monkeylib538.common;

import top.offsetmonkey538.monkeylib538.common.telemetry.TelemetryHandler;
import top.offsetmonkey538.offsetutils538.api.log.OffsetLogger;

import java.util.ServiceLoader;

public final class MonkeyLib538Common {
    public static final String MOD_ID = "monkeylib538";
    public static final OffsetLogger LOGGER = OffsetLogger.create(MOD_ID);

    public static void initialize() {
        TelemetryHandler.initialize();
    }

    public static <T> T load(Class<T> clazz) {
        LOGGER.info("Loading service for: %s", clazz);
        return ServiceLoader.load(clazz, MonkeyLib538Common.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }
}
