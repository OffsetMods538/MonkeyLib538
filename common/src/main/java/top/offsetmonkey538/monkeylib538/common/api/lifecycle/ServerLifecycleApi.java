package top.offsetmonkey538.monkeylib538.common.api.lifecycle;

import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

public interface ServerLifecycleApi {
    ServerLifecycleApi INSTANCE = load(ServerLifecycleApi.class);

    static void runOnServerStarting(final Runnable work) {
        INSTANCE.runOnServerStartingImpl(work);
    }
    static void runOnServerStarted(final Runnable work) {
        INSTANCE.runOnServerStartedImpl(work);
    }
    static void runOnServerStopping(final Runnable work) {
        INSTANCE.runOnServerStoppingImpl(work);
    }
    static void runOnServerStopped(final Runnable work) {
        INSTANCE.runOnServerStoppedImpl(work);
    }

    @Internal
    void runOnServerStartingImpl(final Runnable work);
    @Internal void runOnServerStartedImpl(final Runnable work);
    @Internal void runOnServerStoppingImpl(final Runnable work);
    @Internal void runOnServerStoppedImpl(final Runnable work);
}
