package top.offsetmonkey538.monkeylib538.neoforge.impl.lifecycle;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.api.lifecycle.ServerLifecycleApi;

public final class ServerLifecycleApiImpl implements ServerLifecycleApi {

    @Override
    public void runOnServerStartingImpl(@NotNull Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, event -> work.run());
    }

    @Override
    public void runOnServerStartedImpl(@NotNull Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class, event -> work.run());

    }

    @Override
    public void runOnServerStoppingImpl(@NotNull Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStoppingEvent.class, event -> work.run());

    }

    @Override
    public void runOnServerStoppedImpl(@NotNull Runnable work) {
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, event -> work.run());

    }
}
