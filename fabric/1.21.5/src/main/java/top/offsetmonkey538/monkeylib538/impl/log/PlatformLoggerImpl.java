package top.offsetmonkey538.monkeylib538.impl.log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import top.offsetmonkey538.monkeylib538.api.log.PlatformLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PlatformLoggerImpl implements PlatformLogger {
    private final Logger logger;
    private final Map<LogLevel, List<LogListener>> listeners = new HashMap<>(Map.of(
            LogLevel.DEBUG, new ArrayList<>(),
            LogLevel.INFO, new ArrayList<>(),
            LogLevel.WARN, new ArrayList<>(),
            LogLevel.ERROR, new ArrayList<>()
    ));

    public PlatformLoggerImpl(final Logger logger) {
        this.logger = logger;
    }

    @Override
    public void debug(@NotNull String message) {
        listeners.get(LogLevel.DEBUG).forEach(consumer -> consumer.accept(message, null));
        logger.debug(message);
    }

    @Override
    public void info(@NotNull String message) {
        listeners.get(LogLevel.INFO).forEach(consumer -> consumer.accept(message, null));
        logger.info(message);
    }

    @Override
    public void warn(@NotNull String message) {
        listeners.get(LogLevel.WARN).forEach(consumer -> consumer.accept(message, null));
        logger.warn(message);
    }

    @Override
    public void warn(@NotNull String message, @Nullable Throwable error) {
        listeners.get(LogLevel.WARN).forEach(consumer -> consumer.accept(message, error));
        logger.warn(message, error);
    }

    @Override
    public void error(@NotNull String message) {
        listeners.get(LogLevel.ERROR).forEach(consumer -> consumer.accept(message, null));
        logger.error(message);
    }

    @Override
    public void error(@NotNull String message, @Nullable Throwable error) {
        listeners.get(LogLevel.ERROR).forEach(consumer -> consumer.accept(message, error));
        logger.error(message, error);
    }

    @Override
    public void addListener(@NotNull LogLevel level, @NotNull LogListener listener) {
        listeners.get(level).add(listener);
    }

    @Override
    public void removeListener(@NotNull LogLevel level, @NotNull LogListener listener) {
        listeners.get(level).remove(listener);
    }
}
