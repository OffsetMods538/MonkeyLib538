package top.offsetmonkey538.monkeylib538.neoforge.impl.lifecycle;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ServerLifecycleApi;

public final class ServerLifecycleApiImpl implements ServerLifecycleApi {

    @Override
    public void runOnServerStartingImpl( Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, event -> work.run());
    }

    @Override
    public void runOnServerStartedImpl( Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class, event -> work.run());

    }

    @Override
    public void runOnServerStoppingImpl(Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStoppingEvent.class, event -> work.run());

    }

    @Override
    public void runOnServerStoppedImpl(Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, event -> work.run());

    }
}
