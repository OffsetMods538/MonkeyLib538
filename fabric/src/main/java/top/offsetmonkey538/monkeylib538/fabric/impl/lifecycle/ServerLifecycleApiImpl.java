package top.offsetmonkey538.monkeylib538.fabric.impl.lifecycle;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.lifecycle.ServerLifecycleApi;

public final class ServerLifecycleApiImpl implements ServerLifecycleApi {

    @Override
    public void runOnServerStartingImpl(@NotNull Runnable work) {
        ServerLifecycleEvents.SERVER_STARTING.register(minecraft -> work.run());
    }

    @Override
    public void runOnServerStartedImpl(@NotNull Runnable work) {
        ServerLifecycleEvents.SERVER_STARTED.register(minecraft -> work.run());

    }

    @Override
    public void runOnServerStoppingImpl(@NotNull Runnable work) {
        ServerLifecycleEvents.SERVER_STOPPING.register(minecraft -> work.run());

    }

    @Override
    public void runOnServerStoppedImpl(@NotNull Runnable work) {
        ServerLifecycleEvents.SERVER_STOPPED.register(minecraft -> work.run());

    }
}
