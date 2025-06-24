package top.offsetmonkey538.monkeylib538.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.SyntaxError;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Provides methods for loading and saving {@link Config} classes.
 */
public final class ConfigManager {
    public static final String VERSION_KEY = "!!!version";

    /**
     * Private constructor
     */
    private ConfigManager() {

    }

    public static final Map<String, Config> CONFIGS = new HashMap<>();

    /**
     * Returns the config instance stored in the {@link ConfigManager#CONFIGS} map.
     * <p>
     * Config must be initialized first!
     *
     * @param config an instance of your config class. Used for returning the correct type and getting the config name.
     * @return the config instance stored in the {@link ConfigManager#CONFIGS} map.
     * @param <T> The type of your custom config class.
     */
    public static <T extends Config> T getConfig(final T config) {
        if (!CONFIGS.containsKey(config.getName())) return null;
        //noinspection unchecked
        return (T) CONFIGS.get(config.getName());
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
        if (config.getFilePath().toFile().exists()) config = load(config, errorHandler);

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

        // Load the config from disk
        final JsonObject json;
        try {
            json = jankson.load(configFile);
        } catch (IOException e) {
            errorHandler.accept("Config file '" + config.getFilePath() + "' could not be read!", e);
            CONFIGS.put(config.getName(), config);
            return config;
        } catch (SyntaxError e) {
            errorHandler.accept("Config file '" + config.getFilePath() + "' is formatted incorrectly!", e);
            CONFIGS.put(config.getName(), config);
            return config;
        }


        // Check if version matches the latest version
        applyDatafixers(config, configFile.toPath(), json, jankson, errorHandler);

        // Load config class from the final json
        config = (T) jankson.fromJson(json, config.getClass());

        CONFIGS.put(config.getName(), config);
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
    public static <T extends Config> void save(T config, BiConsumer<String, Exception> errorHandler) {
        CONFIGS.put(config.getName(), config);

        final Jankson jankson = config.configureJankson(Jankson.builder()).build();

        // Convert config to json
        final JsonElement jsonAsElement = jankson.toJson(config);
        if (!(jsonAsElement instanceof final JsonObject json)) {
            errorHandler.accept("Could not cast '" + jsonAsElement.getClass().getName() + "' to 'JsonObject'! Config will not be saved!", null);
            return;
        }

        // Write config version
        json.put(VERSION_KEY, new JsonPrimitive(config.getConfigVersion()), "!!!!! DO NOT MODIFY THIS VALUE !!!!");

        // Convert final json to string
        final String result = json.toJson(true, true);

        try {
            Files.createDirectories(config.getFilePath().getParent());
            Files.writeString(config.getFilePath(), result);
        } catch (IOException e) {
            errorHandler.accept("Config file '" + config.getFilePath() + "' could not be written to!", e);
        }
    }

    /**
     * Applies all the required datafixers upgrade the provided {@link JsonObject} from config version {@code from} to config version {@code to}
     * <br>
     * Loops through a sublist of the provided {@link Datafixer}s and applies them in order
     *
     * @param config Instance of the Config class to datafix for
     * @param configFile The original config file being loaded.
     * @param json The json data to upgrade
     * @param jankson todo: javadoc
     * @param errorHandler A method to handle errors. For example {@code LOGGER::error}.
     * @param <T> The type of your Config class
     */
    private static <T extends Config> void applyDatafixers(T config, Path configFile, JsonObject json, Jankson jankson, BiConsumer<String, Exception> errorHandler) {
        int last_version = json.getInt(VERSION_KEY, 0);
        int current_version = config.getConfigVersion();
        if (last_version < current_version) try {
            final Path backupPath = configFile.resolveSibling(String.format("%s-%s-backup.json", configFile.getFileName(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss"))));
            Files.copy(configFile, backupPath);

            for (Datafixer datafixer : config.getDatafixers().subList(last_version, current_version)) {
                datafixer.apply(json, jankson);
            }
            save(config, errorHandler);
        } catch (IOException e) {
            errorHandler.accept("Unable to create backup of config file '" + configFile + "'! Continuing anyway cause I don't care if your config gets messed up and I can't think of a reason for this even happening cause like the initial config file has to be there so I'd imagine that the directory is writeable so like why wouldn't it be possible to write the backup of the file?", e);
        }

        if (last_version > current_version) {
            throw new IllegalStateException("Config file '" + config.getFilePath() + "' is for a newer version of the mod, please update! Expected config version '" + current_version + "', found '" + last_version + "'!");
        }
    }
}
