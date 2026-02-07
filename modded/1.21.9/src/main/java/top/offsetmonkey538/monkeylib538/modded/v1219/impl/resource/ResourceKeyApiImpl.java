package top.offsetmonkey538.monkeylib538.modded.v1219.impl.resource;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;
import top.offsetmonkey538.monkeylib538.modded.api.resource.ResourceKeyApi;
import top.offsetmonkey538.monkeylib538.modded.v1219.api.wrapper.ModdedVersionIdentifier;

public final class ResourceKeyApiImpl implements ResourceKeyApi {
    @Override
    public <T> ResourceKey<T> createImpl(ResourceKey<? extends Registry<T>> resourceKey, Identifier id) {
        return ResourceKey.create(resourceKey, ModdedVersionIdentifier.get(id));
    }

    @Override
    public <T> ResourceKey<Registry<T>> createRegistryImpl(Identifier id) {
        return ResourceKey.createRegistryKey(ModdedVersionIdentifier.get(id));
    }

    @Override
    public Identifier getLocationImpl(ResourceKey<?> resourceKey) {
        return ModdedVersionIdentifier.of(resourceKey.location());
    }
}
