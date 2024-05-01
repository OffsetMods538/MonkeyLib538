package top.offsetmonkey538.monkeylib538.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.BiConsumer;

/**
 * Provides methods for loading and saving {@link Config} classes.
 */
public final class ConfigManager {

    /**
     * Private constructor
     */
    private ConfigManager() {

    }

    /**
     * Loads the {@link Config} file if and only if the config file exists.
     * <br>
     * Otherwise, it saves the default values to disk.
     *
     * @see #load(Config, BiConsumer)
     * @see #save(Config, BiConsumer)
     * @param config An instance of the {@link Config} containing the default values, usually {@code new MyConfig()}.
     * @param errorHandler A method to handle errors. For example {@code LOGGER::error}.
     * @return either an instance of {@link Config} loaded from disk or the provided default {@link Config}.
     */
    public static <T extends Config> T init(T config, BiConsumer<String, Exception> errorHandler) {
        if (config.getFilePath().toFile().exists()) return load(config, errorHandler);

        save(config, errorHandler);
        return config;
    }

    /**
     * Used for loading a {@link Config} class
     * <br>
     * Logs an error and returns the provided default {@link Config} when the config file could not be read or is formatted incorrectly.
     *
     * @see #init(Config, BiConsumer)
     * @see #save(Config, BiConsumer)
     * @param config An instance of the {@link Config} containing the default values, usually {@code new MyConfig()}.
     * @param errorHandler A method to handle errors. For example {@code LOGGER::error}.
     * @return an instance of the provided {@link Config} class populated from the config file or the provided default {@link Config} when the config file could not be read or is formatted incorrectly.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Config> T load(T config, BiConsumer<String, Exception> errorHandler) {
        final Jankson jankson = config.configureJankson(Jankson.builder()).build();
        final File configFile = config.getFilePath().toFile();

        try {
            return (T) jankson.fromJson(jankson.load(configFile), config.getClass());
        } catch (IOException e) {
            errorHandler.accept("Config file '" + config.getFilePath() + "' could not be read!", e);
        } catch (SyntaxError e) {
            errorHandler.accept("Config file '" + config.getFilePath() + "' is formatted incorrectly!", e);
        }
        
        return config;
    }

    /**
     * Saves the provided instance of {@link Config} onto disk.
     * <br>
     * Errors if it couldn't write to the file.
     *
     * @see #init(Config, BiConsumer)
     * @see #load(Config, BiConsumer)
     * @param config the instance of {@link Config} to save.
     * @param errorHandler A method to handle errors. For example {@code LOGGER::error}.
     */
    public static void save(Config config, BiConsumer<String, Exception> errorHandler) {
        final Jankson jankson = config.configureJankson(Jankson.builder()).build();

        final String result = jankson
                .toJson(config)
                .toJson(true, true);

        try {
            Files.writeString(config.getFilePath(), result);
        } catch (IOException e) {
            errorHandler.accept("Config file '" + config.getFilePath() + "' could not be written to!", e);
        }
    }
}
