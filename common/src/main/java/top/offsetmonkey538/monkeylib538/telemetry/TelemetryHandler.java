package top.offsetmonkey538.monkeylib538.telemetry;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.command.ConfigCommandApi;
import top.offsetmonkey538.monkeylib538.api.lifecycle.ClientLifecycleApi;
import top.offsetmonkey538.monkeylib538.api.lifecycle.ServerLifecycleApi;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;
import top.offsetmonkey538.monkeylib538.api.telemetry.TelemetryRegistry;
import top.offsetmonkey538.monkeylib538.impl.telemetry.TelemetryRegistryImpl;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigManager;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.MOD_ID;
import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.getLogger;

public final class TelemetryHandler {
    private TelemetryHandler() {

    }

    private static boolean hasSent = false;

    private static final ConfigHolder<TelemetryConfig> telemetryConfig = ConfigManager.init(ConfigHolder.create(TelemetryConfig::new, getLogger()::error));

    public static void initialize() {
        TelemetryRegistry.register(MOD_ID);
        ConfigCommandApi.registerConfigCommand(telemetryConfig, null, TelemetryHandler::sendOnNewThread, MOD_ID, "telemetry");

        // Other mods have probably registered themselves for telemetry before either of the two following points. They should just be able to use their default initializers afaik
        // For dedicated servers, I need to use ServerStarted as PlatformUtil$ServerBrandGetter only starts working after ServerStarting happens.
        if (PlatformUtil.isDedicatedServer()) ServerLifecycleApi.runOnServerStarted(TelemetryHandler::sendOnNewThread);
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
                getLogger().error("Failed to send telemetry data :(", e);
            }
        }).start();
    }

    private static void send() throws IOException, InterruptedException {
        final String jsonData = collectAnalytics();
        byte[] data = jsonData.getBytes(StandardCharsets.UTF_8);
        final byte[] compressedData = /* todo: seems pointless I guess: compress(data); */ null;
        if (compressedData != null) data = compressedData;

        final HttpRequest.Builder request = HttpRequest.newBuilder(URI.create("https://analytics.offsetmonkey538.top/ingest"));
        request.POST(HttpRequest.BodyPublishers.ofByteArray(data));
        request.version(HttpClient.Version.HTTP_1_1);
        request.header("User-Agent", "MonkeyLib538/1");
        if (compressedData != null) request.header("Content-Encoding", "gzip");
        request.header("Content-Type", "application/json");

        try (final HttpClient client = HttpClient.newBuilder().build()) {
            final HttpResponse<String> response = client.send(request.build(), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() <= 299) return;

            getLogger().error("Received non-2xx response when trying to send the following telemetry data: %s", jsonData);
            getLogger().error("Full response:\n" + response.body());
        }
    }

    private static @NotNull String collectAnalytics() {
        final JsonObject jsobData = new JsonObject();
        jsobData.addProperty("mc", PlatformUtil.getMinecraftVersion());
        jsobData.addProperty("e", PlatformUtil.isDedicatedServer() ? "s" : "c");
        jsobData.addProperty("l", PlatformUtil.getLoaderName());
        jsobData.add("m", ((TelemetryRegistryImpl) TelemetryRegistry.INSTANCE).registry);

        return jsobData.toString();
    }

    // TODO: delete
    private static byte @Nullable [] compress(final byte[] data) {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (final GZIPOutputStream gzip = new GZIPOutputStream(output)) {
            gzip.write(data);
        } catch (IOException e) {
            getLogger().error("Failed to compress telemetry message, sending uncompressed data instead!", e);
            return null;
        }
        return output.toByteArray();
    }
}
