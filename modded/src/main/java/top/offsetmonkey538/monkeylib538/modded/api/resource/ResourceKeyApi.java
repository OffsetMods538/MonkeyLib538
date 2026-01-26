package top.offsetmonkey538.monkeylib538.modded.api.resource;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ResourceKeyApi {
    @Internal
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


    @Internal <T> ResourceKey<T> createImpl(ResourceKey<? extends Registry<T>> resourceKey, Identifier id);
    @Internal <T> ResourceKey<Registry<T>> createRegistryImpl(Identifier id);
    @Internal Identifier getLocationImpl(ResourceKey<?> resourceKey);
}
