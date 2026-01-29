package top.offsetmonkey538.monkeylib538.modded.v1211.api.wrapper;

import net.minecraft.resources.ResourceLocation;
import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;
import top.offsetmonkey538.monkeylib538.modded.api.wrapper.ModdedIdentifier;

public interface ModdedVersionIdentifier extends ModdedIdentifier {
    static Identifier of(ResourceLocation identifier) {
        return ModdedInstantiator.INSTANCE.of(identifier);
    }

    static ResourceLocation get(Identifier identifier) {
        return ((ModdedVersionIdentifier) identifier).get();
    }

    ResourceLocation get();
}
