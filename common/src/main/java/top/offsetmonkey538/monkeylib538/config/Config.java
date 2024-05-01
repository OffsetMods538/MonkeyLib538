package top.offsetmonkey538.monkeylib538.config;

import blue.endless.jankson.Jankson;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

/**
 * Extend this in your config class.
 */
public abstract class Config {

    /**
     * Configure the provided {@link Jankson.Builder}.
     * May be overridden if needed.
     *
     * @param builder The builder to configure.
     * @return the configured {@link Jankson.Builder}.
     */
    protected Jankson.Builder configureJankson(Jankson.Builder builder) {
        // TODO: Default serializers
        return builder;
    }

    /**
     * May be overridden if needed.
     *
     * @return the path the config should be stored at, including the file extension.
     */
    public Path getFilePath() {
        return FabricLoader.getInstance().getConfigDir().resolve(getName() + ".json");
    }

    /**
     * The name of the config. Used for creating the file path.
     *
     * @return the name of the config. Used for creating the file path.
     */
    protected abstract String getName();
}
