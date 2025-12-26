package top.offsetmonkey538.monkeylib538.telemetry;

import blue.endless.jankson.Comment;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.platform.PlatformUtil;
import top.offsetmonkey538.offsetconfig538.api.config.Config;

import java.nio.file.Path;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.MOD_ID;

public final class TelemetryConfig implements Config {
    @Comment("Boolean for enabling/disabling telemetry, either true or false. Defaults to true")
    public boolean isEnabled = !PlatformUtil.isDevelopmentEnvironment();

    @Override
    public @NotNull Path getConfigDirPath() {
        return PlatformUtil.getConfigDir();
    }

    @Override
    public @NotNull String getId() {
        return MOD_ID + "/telemetry";
    }
}
