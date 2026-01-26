package top.offsetmonkey538.monkeylib538.common.api.log;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.impl.log.MonkeyLibLoggerImpl;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;
import top.offsetmonkey538.offsetutils538.api.errorhandler.ErrorHandler;
import top.offsetmonkey538.offsetutils538.api.text.ArgReplacer;

import static top.offsetmonkey538.offsetutils538.api.text.ArgReplacer.replaceArgs;

/**
 * Provides platform-agnostic logging capabilities.
 * <br />
 * Create a logger using {@link #create(String)}.
 */
public interface MonkeyLibLogger extends ErrorHandler {

    // Implement ErrorHandler so the logger can be used directly
    @Override
    default void handleError(String error, @Nullable Throwable cause) {
        error(error, cause);
    }

    /**
     * Logs the provided message at {@link LogLevel#DEBUG} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The arg for formatting the message
     */
    default void debug(String message, Object arg1) {
        debug(replaceArgs(message, arg1));
    }
    /**
     * Logs the provided message at {@link LogLevel#DEBUG} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The first arg for formatting the message
     * @param arg2 The second arg for formatting the message
     */
    default void debug(String message, Object arg1, Object arg2) {
        debug(replaceArgs(message, arg1, arg2));
    }
    /**
     * Logs the provided message at {@link LogLevel#DEBUG} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void debug(String message, Object... args) {
        debug(replaceArgs(message, args));
    }

    /**
     * Logs the provided message at {@link LogLevel#INFO} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The arg for formatting the message
     */
    default void info(String message, Object arg1) {
        info(replaceArgs(message, arg1));
    }
    /**
     * Logs the provided message at {@link LogLevel#INFO} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The first arg for formatting the message
     * @param arg2 The second arg for formatting the message
     */
    default void info(String message, Object arg1, Object arg2) {
        info(replaceArgs(message, arg1, arg2));
    }
    /**
     * Logs the provided message at {@link LogLevel#INFO} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void info(String message, Object... args) {
        info(replaceArgs(message, args));
    }

    /**
     * Logs the provided message at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The arg for formatting the message
     */
    default void warn(String message, Object arg1) {
        warn(replaceArgs(message, arg1));
    }
    /**
     * Logs the provided message at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The first arg for formatting the message
     * @param arg2 The second arg for formatting the message
     */
    default void warn(String message, Object arg1, Object arg2) {
        warn(replaceArgs(message, arg1, arg2));
    }
    /**
     * Logs the provided message at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void warn(String message, Object... args) {
        warn(replaceArgs(message, args));
    }

    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param arg1 The arg for formatting the message
     */
    default void warn(String message, @Nullable Throwable error, Object arg1) {
        warn(replaceArgs(message, arg1), error);
    }
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param arg1 The first arg for formatting the message
     * @param arg2 The second arg for formatting the message
     */
    default void warn(String message, @Nullable Throwable error, Object arg1, Object arg2) {
        warn(replaceArgs(message, arg1, arg2), error);
    }
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#WARN} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param args The args for formatting the message
     */
    default void warn(String message, @Nullable Throwable error, Object... args) {
        warn(replaceArgs(message, args), error);
    }

    /**
     * Logs the provided message at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The arg for formatting the message
     */
    default void error(String message, Object arg1) {
        error(replaceArgs(message, arg1));
    }
    /**
     * Logs the provided message at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param arg1 The first arg for formatting the message
     * @param arg2 The second arg for formatting the message
     */
    default void error(String message, Object arg1, Object arg2) {
        error(replaceArgs(message, arg1, arg2));
    }
    /**
     * Logs the provided message at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param args The args for formatting the message
     */
    default void error(String message, Object... args) {
        error(replaceArgs(message, args));
    }

    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param arg1 The arg for formatting the message
     */
    default void error(String message, @Nullable Throwable error, Object arg1) {
        error(replaceArgs(message, arg1), error);
    }
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object, Object)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param arg1 The first arg for formatting the message
     * @param arg2 The second arg for formatting the message
     */
    default void error(String message, @Nullable Throwable error, Object arg1, Object arg2) {
        error(replaceArgs(message, arg1, arg2), error);
    }
    /**
     * Logs the provided message and {@link Throwable} at {@link LogLevel#ERROR} level.
     * <br />
     * Uses {@link ArgReplacer#replaceArgs(String, Object...)} for formatting the message.
     *
     * @param message The message to log
     * @param error The throwable to log
     * @param args The args for formatting the message
     */
    default void error(String message, @Nullable Throwable error, Object... args) {
        error(replaceArgs(message, args), error);
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
     * @return the provided {@code listener}
     */
    LogListener addListener(LogLevel level, LogListener listener);
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
     *
     * @param id the id of the logger.
     * @return a logger for the provided id
     */
    static MonkeyLibLogger create(final String id) {
        return MonkeyLibLogger.Provider.INSTANCE.create(id);
    }

    @Internal
    interface Provider {
        // Can't service load here cause service loader uses the logger
        Provider INSTANCE = new MonkeyLibLoggerImpl.ProviderImpl();
        MonkeyLibLogger create(final String id);
    }
}
