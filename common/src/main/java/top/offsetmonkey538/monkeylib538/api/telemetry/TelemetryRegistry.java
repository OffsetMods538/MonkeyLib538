package top.offsetmonkey538.monkeylib538.api.telemetry;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.lifecycle.ServerLifecycleApi;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface TelemetryRegistry {
    /**
     * The instance
     */
    @ApiStatus.Internal
    TelemetryRegistry INSTANCE = load(TelemetryRegistry.class);

    static void register(final @NotNull String modId) {
        INSTANCE.registerImpl(modId);
    }

    void registerImpl(final @NotNull String modId);
}
