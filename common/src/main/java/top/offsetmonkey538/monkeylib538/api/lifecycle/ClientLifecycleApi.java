package top.offsetmonkey538.monkeylib538.api.lifecycle;

import top.offsetmonkey538.monkeylib538.api.platform.LoaderUtil;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.getLogger;
import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface ClientLifecycleApi {
    ClientLifecycleApi INSTANCE = LoaderUtil.isDedicatedServer() ? new ServerImpl() : load(ClientLifecycleApi.class);

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

    void runOnLoadingFinishedImpl(final Runnable work);

    final class ServerImpl implements ClientLifecycleApi {
        private ServerImpl() {

        }

        @Override
        public void runOnLoadingFinishedImpl(Runnable work) {
            getLogger().warn("ClientLifecycleApi#runOnLoadingFinished called from non-client environment!", new Throwable());
        }
    }
}
