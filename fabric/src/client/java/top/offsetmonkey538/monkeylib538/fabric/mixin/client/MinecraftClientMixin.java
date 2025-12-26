package top.offsetmonkey538.monkeylib538.fabric.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.monkeylib538.fabric.impl.lifecycle.ClientLifecycleApiImpl;

import java.util.List;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Inject(
            method = "collectLoadTimes",
            at = @At("TAIL")
    )
    private void monkeylib538$runClientLoadFinishEvent(CallbackInfo ci) {
        final List<Runnable> work = ClientLifecycleApiImpl.getAndDestroyWorkToRunOnLoadingFinished();
        if (work != null) work.forEach(Runnable::run);
    }
}
