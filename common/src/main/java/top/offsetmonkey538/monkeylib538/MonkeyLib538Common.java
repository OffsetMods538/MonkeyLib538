package top.offsetmonkey538.monkeylib538;

import java.util.ServiceLoader;

public final class MonkeyLib538Common {
    private MonkeyLib538Common() {

    }

    public static <T> T load(Class<T> clazz) {
        return ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to load service for " + clazz.getName()));
    }
}
