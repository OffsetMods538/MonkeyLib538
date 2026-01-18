package top.offsetmonkey538.monkeylib538.modded.v1219.impl.wrapper;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;
import top.offsetmonkey538.monkeylib538.modded.v1219.api.wrapper.ModdedVersionIdentifier;

public record IdentifierWrapper(ResourceLocation vanillaIdentifier) implements ModdedVersionIdentifier {
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
    public ResourceLocation get() {
        return vanillaIdentifier;
    }

    public static final class CodecProviderImpl implements CodecProvider {
        @Override
        public Codec<Identifier> get() {
            return ResourceLocation.CODEC.xmap(IdentifierWrapper::new, ModdedVersionIdentifier::get);
        }
    }

    public static final class InstantiatorImpl implements ModdedInstantiator {
        @Override
        public ModdedVersionIdentifier of(String location) {
            return new IdentifierWrapper(ResourceLocation.parse(location));
        }

        @Override
        public ModdedVersionIdentifier of(String namespace, String path) {
            return new IdentifierWrapper(ResourceLocation.fromNamespaceAndPath(namespace, path));
        }

        @Override
        public Identifier of(Object identifier) {
            return new IdentifierWrapper((ResourceLocation) identifier);
        }
    }
}
