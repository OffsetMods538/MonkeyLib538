package top.offsetmonkey538.monkeylib538.paper.impl.lifecycle;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ServerLifecycleApi;
import top.offsetmonkey538.monkeylib538.paper.impl.platform.LoaderUtilImpl;
import top.offsetmonkey538.offsetconfig538.api.event.Event;

public final class ServerLifecycleApiImpl implements ServerLifecycleApi {
    public static final Event<Runnable> STOPPING = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });
    public static final Event<Runnable> STOPPED = Event.createEvent(Runnable.class, handlers -> () -> {
        for (final Runnable work : handlers) work.run();
    });

    @Override
    public void runOnServerStartingImpl(Runnable work) {
        // Assume this must be called during plugin init and that's when the server is starting
        work.run();
    }

    @Override
    public void runOnServerStartedImpl(Runnable work) {
        Bukkit.getPluginManager().registerEvents(new ServerStartedEventHandler(work), LoaderUtilImpl.getPlugin());
    }

    private record ServerStartedEventHandler(Runnable work) implements Listener {
        @EventHandler(priority = EventPriority.MONITOR)
        public void onServerLoad(final ServerLoadEvent event) {
            if (event.getType() == ServerLoadEvent.LoadType.STARTUP) work.run();
        }
    }

    @Override
    public void runOnServerStoppingImpl(Runnable work) {
        STOPPING.listen(work);
    }

    @Override
    public void runOnServerStoppedImpl(Runnable work) {
        STOPPED.listen(work);
    }
}
