package top.offsetmonkey538.monkeylib538.api.log;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface PlatformLoggerProvider {
    PlatformLoggerProvider INSTANCE = load(PlatformLoggerProvider.class);

    /**
     * Provides a logger for the provided id
     * <br />
     * <strong>Must equal either the modid on fabric and neoforge or the plugin name or logger prefix on paper!</strong>
     *
     * @param id the id of the logger.
     * @return a logger for the provided id
     */
    PlatformLogger createLogger(final String id);
}
