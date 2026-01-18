package top.offsetmonkey538.monkeylib538.modded.api.resource;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ResourceKeyApi {
    ResourceKeyApi INSTANCE = load(ResourceKeyApi.class);

    static <T> ResourceKey<T> create(ResourceKey<? extends Registry<T>> resourceKey, Identifier id) {
        return INSTANCE.createImpl(resourceKey, id);
    }
    static <T> ResourceKey<Registry<T>> createRegistry(Identifier id) {
        return INSTANCE.createRegistryImpl(id);
    }

    static Identifier getLocation(ResourceKey<?> resourceKey) {
        return INSTANCE.getLocationImpl(resourceKey);
    }


    <T> ResourceKey<T> createImpl(ResourceKey<? extends Registry<T>> resourceKey, Identifier id);
    <T> ResourceKey<Registry<T>> createRegistryImpl(Identifier id);
    Identifier getLocationImpl(ResourceKey<?> resourceKey);
}
