package top.offsetmonkey538.monkeylib538.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.api.SyntaxError;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538.*;

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
     * Used for loading a {@link Config} class
     * <br>
     * Logs an error and returns the provided default {@link Config} when the config file could not be read or is formatted incorrectly.
     *
     * @param config An instance of the {@link Config} containing the default values, usually {@code new MyConfig()}.
     * @return an instance of the provided {@link Config} class populated from the config file or the provided default {@link Config} when the config file could not be read or is formatted incorrectly.
     */
    public static Config load(Config config) {
        final Jankson jankson = config.configureJankson(Jankson.builder()).build();
        final File configFile = config.getFilePath().toFile();

        try {
            return jankson.fromJson(jankson.load(configFile), config.getClass());
        } catch (IOException e) {
            LOGGER.error("Config file '" + config.getFilePath() + "' could not be read!", e);
        } catch (SyntaxError e) {
            LOGGER.error("Config file '" + config.getFilePath() + "' is formatted incorrectly!", e);
        }
        
        return config;
    }

    /**
     * Saves the provided instance of {@link Config} onto disk.
     * <br>
     * Logs an error if it couldn't write to the file.
     *
     * @param config the instance of {@link Config} to save.
     */
    public static void save(Config config) {
        final Jankson jankson = config.configureJankson(Jankson.builder()).build();

        final String result = jankson
                .toJson(config)
                .toJson(true, true);

        try {
            Files.writeString(config.getFilePath(), result);
        } catch (IOException e) {
            LOGGER.error("Config file '" + config.getFilePath() + "' could not be written to!", e);
        }
    }
}
