package top.offsetmonkey538.monkeylib538.fabric.impl.server;

import net.minecraft.server.dedicated.DedicatedServer;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.fabric.MonkeyLib538Initializer;
import top.offsetmonkey538.monkeylib538.modded.api.server.ServerProvider;

public final class ServerProviderImpl implements ServerProvider {

    @Override
    public @Nullable DedicatedServer getServerImpl() {
        return MonkeyLib538Initializer.getServer();
    }
}
