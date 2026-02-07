package top.offsetmonkey538.monkeylib538.modded.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ClientLifecycleApi;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {

    @Inject(
            method = "onGameLoadFinished",
            at = @At("TAIL")
    )
    private void monkeylib538$runClientLoadFinishEvent(CallbackInfo ci) {
        ClientLifecycleApi.LOAD_FINISHED.getInvoker().run();
    }
}
