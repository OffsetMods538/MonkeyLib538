package top.offsetmonkey538.monkeylib538.common.impl.telemetry;

import com.google.gson.JsonArray;
import top.offsetmonkey538.monkeylib538.common.api.telemetry.TelemetryRegistry;

public final class TelemetryRegistryImpl implements TelemetryRegistry {
    public final JsonArray registry = new JsonArray();

    public TelemetryRegistryImpl() {

    }

    @Override
    public void registerImpl(String modId) {
        registry.add(modId);
    }
}
