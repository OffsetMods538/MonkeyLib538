package top.offsetmonkey538.monkeylib538.neoforge.impl.lifecycle;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ClientLifecycleApi;

import java.util.ArrayList;
import java.util.List;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.getLogger;

public final class ClientLifecycleApiImpl implements ClientLifecycleApi {
    private static @Nullable List<Runnable> workToRunOnLoadingFinished = new ArrayList<>();

    @Override
    public void runOnLoadingFinishedImpl(Runnable work) {
        if (workToRunOnLoadingFinished == null) {
            getLogger().error("runOnLoadingFinished called after loading has already finished!", new Throwable());
            return;
        }

        workToRunOnLoadingFinished.add(work);
    }

    public static @Nullable List<Runnable> getAndDestroyWorkToRunOnLoadingFinished() {
        if (workToRunOnLoadingFinished == null) {
            getLogger().error("getAndDestroyWorkToRunOnLoadingFinished called twice? I don't think loading should be able to finish twice...", new Throwable());
            return null;
        }

        final List<Runnable> result = workToRunOnLoadingFinished;
        workToRunOnLoadingFinished = null;
        return result;
    }
}
