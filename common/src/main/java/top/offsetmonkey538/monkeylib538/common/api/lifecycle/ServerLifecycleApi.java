package top.offsetmonkey538.monkeylib538.common.api.lifecycle;

import top.offsetmonkey538.offsetutils538.api.event.Event;

public final class ServerLifecycleApi {
    private ServerLifecycleApi() {}

    public static final Event<Runnable> STARTING = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });
    public static final Event<Runnable> STARTED = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });
    public static final Event<Runnable> STOPPING = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });
    public static final Event<Runnable> STOPPED = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });
}
