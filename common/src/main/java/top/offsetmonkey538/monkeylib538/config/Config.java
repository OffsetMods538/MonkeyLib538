package top.offsetmonkey538.monkeylib538.config;

import blue.endless.jankson.Jankson;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;
import java.util.List;

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
     * Provides a list of {@link Datafixer}s for this config.
     * <br>
     * First entry (0) is upgrading from 0 to 1,
     * Second entry (1) is upgrading from 1 to 2,
     * Third entry (2) is upgrading from 2 to 3,
     * ...
     *
     * @return a list of {@link Datafixer}s for this config.
     * @see #getConfigVersion()
     */
    protected List<Datafixer> getDatafixers() {
        return List.of();
    }

    /**
     * Get the version of this config.
     * <br>
     * Used for datafixing
     *
     * @return get the version for this config.
     * @see #getDatafixers()
     */
    protected int getConfigVersion() {
        return 0;
    }

    /**
     * The name of the config. Used for creating the file path.
     *
     * @return the name of the config. Used for creating the file path.
     */
    protected abstract String getName();
}
