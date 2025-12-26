package top.offsetmonkey538.monkeylib538.impl.telemetry;

import com.google.gson.JsonArray;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.telemetry.TelemetryRegistry;

public final class TelemetryRegistryImpl implements TelemetryRegistry {
    public final JsonArray registry = new JsonArray(1);

    /**
     * Public no-args constructor for java service magic to call
     */
    public TelemetryRegistryImpl() {

    }

    @Override
    public void registerImpl(@NotNull String modId) {
        registry.add(modId);
    }
}
