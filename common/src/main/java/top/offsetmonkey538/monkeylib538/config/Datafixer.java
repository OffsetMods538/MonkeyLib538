package top.offsetmonkey538.monkeylib538.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;

/**
 * A functional interface that is used to update between different versions of a config file
 */
@FunctionalInterface
public interface Datafixer {

    /**
     * Modify the provided {@link JsonObject} to comply with the correct version of the config
     *
     * @param original The json data in the format of the last config version
     * @param jankson todo: add javadoc
     */
    void apply(JsonObject original, Jankson jankson);
}
