package top.offsetmonkey538.monkeylib538.common.api.lifecycle;

import top.offsetmonkey538.offsetutils538.api.event.Event;

public final class ClientLifecycleApi {
    private ClientLifecycleApi() {}

    /**
     * Runs the provided work when client finishes loading (right before loading screen fades to title screen)
     */
    public static final Event<Runnable> LOAD_FINISHED = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });
}
