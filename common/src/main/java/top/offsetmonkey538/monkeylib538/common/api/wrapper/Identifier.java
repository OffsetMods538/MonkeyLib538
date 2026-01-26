package top.offsetmonkey538.monkeylib538.common.api.wrapper;

import com.mojang.serialization.Codec;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

/**
 * Wrapper around a vanilla Identifier/ResourceLocation.
 */
public interface Identifier {
    /**
     * Codec for this class
     */
    Codec<Identifier> CODEC = load(CodecProvider.class).get();

    /**
     * Returns the namespace of this Identifier.
     * <br>
     * Examples: {@code minecraft}, {@code monkeylib538}.
     *
     * @return the namespace of this Identifier
     */
    String getNamespace();
    /**
     * Returns the path of this Identifier.
     *
     * @return the path of this Identifier
     */
    String getPath();

    /**
     * Creates an identifier from the provided location.
     * <p>The location must separate the namespace and path with a colon like so {@code "namespace:path"}</p>
     *
     * @param location the location to parse
     * @return an identifier from the provided location
     */
    static Identifier of(String location) {
        return Instantiator.INSTANCE.of(location);
    }

    /**
     * Creates an identifier from the provided namespace and path.
     *
     * @param namespace the namespace of the identifier
     * @param path the path of the identifier
     * @return an identifier from the provided namespace and path
     */
    static Identifier of(String namespace, String path) {
        return Instantiator.INSTANCE.of(namespace, path);
    }

    @Internal
    interface Instantiator {
        Instantiator INSTANCE = load(Instantiator.class);

        Identifier of(String location);
        Identifier of(String namespace, String path);
    }

    @Internal
    interface CodecProvider {
        CodecProvider INSTANCE = load(CodecProvider.class);

        Codec<Identifier> get();
    }
}
