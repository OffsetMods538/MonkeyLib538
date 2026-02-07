package top.offsetmonkey538.monkeylib538.common.api.telemetry;

import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface TelemetryRegistry {
    @Internal
    TelemetryRegistry INSTANCE = load(TelemetryRegistry.class);

    static void register(final String modId) {
        INSTANCE.registerImpl(modId);
    }

    @Internal
    void registerImpl(final String modId);
}
