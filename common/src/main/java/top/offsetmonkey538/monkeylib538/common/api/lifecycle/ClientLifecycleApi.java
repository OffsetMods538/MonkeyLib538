package top.offsetmonkey538.monkeylib538.common.api.lifecycle;

import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.getLogger;
import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

@FunctionalInterface
public interface ClientLifecycleApi {
    @Internal
    ClientLifecycleApi INSTANCE = LoaderUtil.isDedicatedServer() ? work -> getLogger().warn("ClientLifecycleApi#runOnLoadingFinished called from non-client environment!", new Throwable()) : load(ClientLifecycleApi.class);

    /**
     * Runs the provided work when client finishes loading (right before loading screen fades to title screen)
     * <br>
     * Logs a warning and doesn't run the work when called in an environment where {@link LoaderUtil#isDedicatedServer()} returns {@code true}.
     *
     * @param work the runnable to run.
     */
    static void runOnLoadingFinished(final Runnable work) {
        INSTANCE.runOnLoadingFinishedImpl(work);
    }

    @Internal void runOnLoadingFinishedImpl(final Runnable work);
}
