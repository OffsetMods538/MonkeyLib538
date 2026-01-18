package top.offsetmonkey538.monkeylib538.common.api.wrapper;

import com.mojang.serialization.Codec;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

/**
 * Wrapper around a vanilla Identifier/ResourceLocation.
 */
public interface Identifier {
    Codec<Identifier> CODEC = load(CodecProvider.class).get();

    String getNamespace();
    String getPath();
    String asString();

    static Identifier of(String location) {
        return Instantiator.INSTANCE.of(location);
    }

    static Identifier of(String namespace, String path) {
        return Instantiator.INSTANCE.of(namespace, path);
    }

    interface Instantiator {
        Instantiator INSTANCE = load(Instantiator.class);

        Identifier of(String location);
        Identifier of(String namespace, String path);
    }

    interface CodecProvider {
        CodecProvider INSTANCE = load(CodecProvider.class);

        Codec<Identifier> get();
    }
}
