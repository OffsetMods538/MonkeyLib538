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

    Path getConfigDirImpl();
}
