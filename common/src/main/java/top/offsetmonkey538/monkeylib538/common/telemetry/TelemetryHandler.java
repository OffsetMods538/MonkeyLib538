package top.offsetmonkey538.monkeylib538.common.telemetry;

import com.google.gson.JsonObject;
import top.offsetmonkey538.monkeylib538.common.api.command.ConfigCommandApi;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ClientLifecycleApi;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ServerLifecycleApi;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;
import top.offsetmonkey538.monkeylib538.common.api.telemetry.TelemetryRegistry;
import top.offsetmonkey538.monkeylib538.common.impl.telemetry.TelemetryRegistryImpl;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.MOD_ID;
import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.LOGGER;

public final class TelemetryHandler {
    private TelemetryHandler() {

    }

    private static boolean hasSent = false;

    private static final ConfigHolder<TelemetryConfig> telemetryConfig = ConfigManager.init(ConfigHolder.create(TelemetryConfig::new, LOGGER::error));

    public static void initialize() {
        TelemetryRegistry.register(MOD_ID);
        ConfigCommandApi.registerConfigCommand(telemetryConfig, null, TelemetryHandler::sendOnNewThread, MOD_ID, "telemetry");

        // Other mods have probably registered themselves for telemetry before either of the two following points. They should just be able to use their default initializers afaik
        // For dedicated servers, I need to use ServerStarted as LoaderUtil$ServerBrandGetter only starts working after ServerStarting happens.
        if (LoaderUtil.isDedicatedServer()) ServerLifecycleApi.runOnServerStarted(TelemetryHandler::sendOnNewThread);
        // For clients, running once loading finishes is probably fine.
        else ClientLifecycleApi.runOnLoadingFinished(TelemetryHandler::sendOnNewThread);
    }

    public static void sendOnNewThread() {
        // TODO: maybe should have some better way of creating the thread?
        new Thread(() -> {
            if (telemetryConfig.get().isEnabled && !hasSent) try {
                TelemetryHandler.send();
                hasSent = true;
            } catch (Exception e) {
                LOGGER.error("Failed to send telemetry data :(", e);
            }
        }).start();
    }

    private static void send() throws IOException, InterruptedException {
        final String jsonData = collectAnalytics();
        byte[] data = jsonData.getBytes(StandardCharsets.UTF_8);

        final HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("https://analytics.offsetmonkey538.top/ingest"));
        request.POST(HttpRequest.BodyPublishers.ofByteArray(data));
        request.version(HttpClient.Version.HTTP_1_1);
        request.header("User-Agent", "MonkeyLib538/1");
        request.header("Content-Type", "application/json");

        try (final HttpClient client = HttpClient.newBuilder().build()) {
            final HttpResponse<String> response = client.send(request.build(), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() <= 299) return;

            LOGGER.error("Received non-2xx response when trying to send the following telemetry data: %s", jsonData);
            LOGGER.error("Full response:\n%s", response.body());
        }
    }

    private static String collectAnalytics() {
        final JsonObject jsobData = new JsonObject();
        jsobData.addProperty("mc", LoaderUtil.getMinecraftVersion());
        jsobData.addProperty("e", LoaderUtil.isDedicatedServer() ? "s" : "c");
        jsobData.addProperty("l", LoaderUtil.getLoaderName() == null ? "unknown" : LoaderUtil.getLoaderName());
        jsobData.add("m", ((TelemetryRegistryImpl) TelemetryRegistry.INSTANCE).registry);

        return jsobData.toString();
    }
}
