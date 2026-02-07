package top.offsetmonkey538.monkeylib538.modded.v12111.api.wrapper;

import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;
import top.offsetmonkey538.monkeylib538.modded.api.wrapper.ModdedIdentifier;

public interface ModdedVersionIdentifier extends ModdedIdentifier {
    static Identifier of(net.minecraft.resources.Identifier identifier) {
        return ModdedInstantiator.INSTANCE.of(identifier);
    }

    static net.minecraft.resources.Identifier get(Identifier identifier) {
        return ((ModdedVersionIdentifier) identifier).get();
    }

    net.minecraft.resources.Identifier get();
}
