package top.offsetmonkey538.monkeylib538.modded.v12111.impl.wrapper;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import top.offsetmonkey538.monkeylib538.modded.v12111.api.wrapper.ModdedVersionIdentifier;

public record IdentifierWrapper(Identifier vanillaIdentifier) implements ModdedVersionIdentifier {
    @Override
    public String getNamespace() {
        return vanillaIdentifier.getNamespace();
    }

    @Override
    public String getPath() {
        return vanillaIdentifier.getPath();
    }

    @Override
    public String asString() {
        return vanillaIdentifier.toString();
    }

    @Override
    public String toString() {
        return asString();
    }

    @Override
    public Identifier get() {
        return vanillaIdentifier;
    }

    public static final class CodecProviderImpl implements CodecProvider {
        @Override
        public Codec<top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier> get() {
            return Identifier.CODEC.xmap(IdentifierWrapper::new, ModdedVersionIdentifier::get);
        }
    }

    public static final class InstantiatorImpl implements ModdedInstantiator {
        @Override
        public ModdedVersionIdentifier of(String location) {
            return new IdentifierWrapper(Identifier.parse(location));
        }

        @Override
        public ModdedVersionIdentifier of(String namespace, String path) {
            return new IdentifierWrapper(Identifier.fromNamespaceAndPath(namespace, path));
        }

        @Override
        public top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier of(Object identifier) {
            return new IdentifierWrapper((net.minecraft.resources.Identifier) identifier);
        }
    }
}
