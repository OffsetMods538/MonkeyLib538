package top.offsetmonkey538.monkeylib538.modded.api.wrapper;

import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;
import top.offsetmonkey538.monkeylib538.common.api.wrapper.Identifier;

public interface ModdedIdentifier extends Identifier {
    static Identifier of(Object identifier) {
        return ModdedInstantiator.INSTANCE.of(identifier);
    }

    @Internal
    interface ModdedInstantiator extends Instantiator {
        ModdedInstantiator INSTANCE = (ModdedInstantiator) Instantiator.INSTANCE;

        Identifier of(Object identifier);
    }
}
