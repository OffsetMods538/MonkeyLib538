package top.offsetmonkey538.monkeylib538.common.telemetry;

import blue.endless.jankson.Comment;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;
import top.offsetmonkey538.offsetutils538.api.config.Config;

import java.nio.file.Path;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.MOD_ID;

public final class TelemetryConfig implements Config {
    @Comment("Boolean for enabling/disabling telemetry, either true or false. Defaults to true")
    public boolean isEnabled = !LoaderUtil.isDevelopmentEnvironment();

    @Override
    public Path getConfigDirPath() {
        return LoaderUtil.getConfigDir();
    }

    @Override
    public String getId() {
        return MOD_ID + "/telemetry";
    }
}
