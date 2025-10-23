package top.offsetmonkey538.monkeylib538.api.lifecycle;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface ServerLifecycleApi {
    /**
     * The instance
     */
    @ApiStatus.Internal
    ServerLifecycleApi INSTANCE = load(ServerLifecycleApi.class);

    static void runOnServerStarting(final @NotNull Runnable work) {
        INSTANCE.runOnServerStartingImpl(work);
    }
    static void runOnServerStarted(final @NotNull Runnable work) {
        INSTANCE.runOnServerStartedImpl(work);
    }
    static void runOnServerStopping(final @NotNull Runnable work) {
        INSTANCE.runOnServerStoppingImpl(work);
    }
    static void runOnServerStopped(final @NotNull Runnable work) {
        INSTANCE.runOnServerStoppedImpl(work);
    }

    void runOnServerStartingImpl(final @NotNull Runnable work);
    void runOnServerStartedImpl(final @NotNull Runnable work);
    void runOnServerStoppingImpl(final @NotNull Runnable work);
    void runOnServerStoppedImpl(final @NotNull Runnable work);
}
