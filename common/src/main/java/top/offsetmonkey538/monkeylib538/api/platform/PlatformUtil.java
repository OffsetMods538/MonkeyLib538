package top.offsetmonkey538.monkeylib538.api.platform;

import org.jetbrains.annotations.ApiStatus;

import java.nio.file.Path;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface PlatformUtil {
    /**
     * The instance
     */
    @ApiStatus.Internal
    PlatformUtil INSTANCE = load(PlatformUtil.class);

    static Path getConfigDir() {
        return INSTANCE.getConfigDirImpl();
    }
    static Path getModsDir() {
        return INSTANCE.getModsDirImpl();
    }
    static Path getGameDir() {
        return INSTANCE.getGameDirImpl();
    }

    static boolean isDevelopmentEnvironment() {
        return INSTANCE.isDevelopmentEnvironmentImpl();
    }

    Path getConfigDirImpl();
    Path getModsDirImpl();
    Path getGameDirImpl();
    boolean isDevelopmentEnvironmentImpl();
}
