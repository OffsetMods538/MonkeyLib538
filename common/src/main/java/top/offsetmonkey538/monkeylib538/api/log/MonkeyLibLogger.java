package top.offsetmonkey538.monkeylib538.api.log;

import org.jspecify.annotations.Nullable;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

/**
 * Provides platform-agnostic logging capabilities.
 * <br />
 * Create a logger using {@link #create(String)}.
 */
public interface MonkeyLibLogger {
    /**
     * Logs the provided message at {@link LogLevel#DEBUG} level.
     * <br />
     * Uses {@link String#formatted(Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void debug(String message, Object... args) {
        debug(String.format(message, args));
    }
    /**
     * Logs the provided message at {@link LogLevel#INFO} level.
     * <br />
     * Uses {@link String#formatted(Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void info(String message, Object... args) {
        info(String.format(message, args));
    }
    /**
     * Logs the provided message at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link String#formatted(Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void warn(String message, Object... args) {
        warn(String.format(message, args));
    }
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link String#formatted(Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param args The args for formatting the message
     */
    default void warn(String message, @Nullable Throwable error, Object... args) {
        warn(String.format(message, args), error);
    }
    /**
     * Logs the provided message at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link String#formatted(Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void error(String message, Object... args) {
        error(String.format(message, args));
    }
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link String#formatted(Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param args The args for formatting the message
     */
    default void error(String message, @Nullable Throwable error, Object... args) {
        error(String.format(message, args), error);
    }

    /**
     * Logs the provided message at {@link LogLevel#DEBUG} level.
     *
     * @param message The message to log
     */
    void debug(String message);
    /**
     * Logs the provided message at {@link LogLevel#INFO} level.
     *
     * @param message The message to log
     */
    void info(String message);
    /**
     * Logs the provided message at {@link LogLevel#WARN} level.
     *
     * @param message The message to log
     */
    void warn(String message);
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#WARN} level.
     *
     * @param message The message to log
     * @param error The throwable to log
     */
    void warn(String message, @Nullable Throwable error);
    /**
     * Logs the provided message at {@link LogLevel#ERROR} level.
     *
     * @param message The message to log
     */
    void error(String message);
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#ERROR} level.
     *
     * @param message The message to log
     * @param error The throwable to log
     */
    void error(String message, @Nullable Throwable error);

    /**
     * Adds a {@link LogListener} to this logger.
     *
     * @param level the level to listen at
     * @param listener the {@link LogListener} to add
     */
    void addListener(LogLevel level, LogListener listener);
    /**
     * Removes a {@link LogListener} from this logger.
     *
     * @param level the level it was listening at
     * @param listener the {@link LogListener} to remove
     */
    void removeListener(LogLevel level, LogListener listener);

    /**
     * Log level
     */
    enum LogLevel {
        /**
         * Debug messages
         */
        DEBUG,
        /**
         * Info messages
         */
        INFO,
        /**
         * Warning messages
         */
        WARN,
        /**
         * Error messages
         */
        ERROR
    }

    /**
     * A log listener allows reading log messages.
     */
    @FunctionalInterface
    interface LogListener {
        /**
         * Accept the message and optionally a {@link Throwable} that was logged.
         *
         * @param message the message that was logged
         * @param error the {@link Throwable} that was logged
         */
        void accept(String message, @Nullable Throwable error);
    }

    /**
     * Creates a logger for the provided id
     * <br />
     * <strong>Must equal either the modid on fabric and neoforge or the plugin name or logger prefix on paper!</strong>
     *
     * @param id the id of the logger.
     * @return a logger for the provided id
     */
    static MonkeyLibLogger create(final String id) {
        return MonkeyLibLogger.Provider.INSTANCE.create(id);
    }

    /**
     * Provides a method for creating a {@link MonkeyLibLogger}
     */
    interface Provider {
        Provider INSTANCE = load(Provider.class);
        MonkeyLibLogger create(final String id);
    }
}
