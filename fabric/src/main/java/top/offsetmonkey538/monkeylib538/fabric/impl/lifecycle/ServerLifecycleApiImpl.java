package top.offsetmonkey538.monkeylib538.fabric.impl.lifecycle;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import top.offsetmonkey538.monkeylib538.api.lifecycle.ServerLifecycleApi;

public final class ServerLifecycleApiImpl implements ServerLifecycleApi {

    @Override
    public void runOnServerStartingImpl(Runnable work) {
        ServerLifecycleEvents.SERVER_STARTING.register(minecraft -> work.run());
    }

    @Override
    public void runOnServerStartedImpl(Runnable work) {
        ServerLifecycleEvents.SERVER_STARTED.register(minecraft -> work.run());

    }

    @Override
    public void runOnServerStoppingImpl(Runnable work) {
        ServerLifecycleEvents.SERVER_STOPPING.register(minecraft -> work.run());

    }

    @Override
    public void runOnServerStoppedImpl(Runnable work) {
        ServerLifecycleEvents.SERVER_STOPPED.register(minecraft -> work.run());

    }
}
