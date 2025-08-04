package top.offsetmonkey538.monkeylib538.api.log;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    default void debug(@NotNull String message, @NotNull Object... args) {
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
    default void info(@NotNull String message, @NotNull Object... args) {
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
    default void warn(@NotNull String message, @NotNull Object... args) {
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
    default void warn(@NotNull String message, @Nullable Throwable error, @NotNull Object... args) {
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
    default void error(@NotNull String message, @NotNull Object... args) {
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
    default void error(@NotNull String message, @Nullable Throwable error, @NotNull Object... args) {
        error(String.format(message, args), error);
    }

    /**
     * Logs the provided message at {@link LogLevel#DEBUG} level.
     *
     * @param message The message to log
     */
    void debug(@NotNull String message);
    /**
     * Logs the provided message at {@link LogLevel#INFO} level.
     *
     * @param message The message to log
     */
    void info(@NotNull String message);
    /**
     * Logs the provided message at {@link LogLevel#WARN} level.
     *
     * @param message The message to log
     */
    void warn(@NotNull String message);
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#WARN} level.
     *
     * @param message The message to log
     * @param error The throwable to log
     */
    void warn(@NotNull String message, @Nullable Throwable error);
    /**
     * Logs the provided message at {@link LogLevel#ERROR} level.
     *
     * @param message The message to log
     */
    void error(@NotNull String message);
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#ERROR} level.
     *
     * @param message The message to log
     * @param error The throwable to log
     */
    void error(@NotNull String message, @Nullable Throwable error);

    /**
     * Adds a {@link LogListener} to this logger.
     *
     * @param level the level to listen at
     * @param listener the {@link LogListener} to add
     */
    void addListener(@NotNull LogLevel level, @NotNull LogListener listener);
    /**
     * Removes a {@link LogListener} from this logger.
     *
     * @param level the level it was listening at
     * @param listener the {@link LogListener} to remove
     */
    void removeListener(@NotNull LogLevel level, @NotNull LogListener listener);

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
    static @NotNull MonkeyLibLogger create(final @NotNull String id) {
        return MonkeyLibLogger.Provider.INSTANCE.create(id);
    }

    /**
     * Provides a method for creating a {@link MonkeyLibLogger}
     */
    @ApiStatus.Internal
    interface Provider {
        /**
         * The instance
         */
        Provider INSTANCE = load(Provider.class);

        /**
         * Creates a logger for the provided id
         * <br />
         * <strong>Must equal either the modid on fabric and neoforge or the plugin name or logger prefix on paper!</strong>
         *
         * @param id the id of the logger.
         * @return a logger for the provided id
         */
        MonkeyLibLogger create(final @NotNull String id);
    }
}
