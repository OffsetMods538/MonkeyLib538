package top.offsetmonkey538.monkeylib538.common.api.telemetry;


import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface TelemetryRegistry {
    TelemetryRegistry INSTANCE = load(TelemetryRegistry.class);

    static void register(final String modId) {
        INSTANCE.registerImpl(modId);
    }

    void registerImpl(final String modId);
}
