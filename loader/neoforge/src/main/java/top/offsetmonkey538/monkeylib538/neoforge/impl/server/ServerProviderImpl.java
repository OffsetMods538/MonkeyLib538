package top.offsetmonkey538.monkeylib538.neoforge.impl.server;

import net.minecraft.server.dedicated.DedicatedServer;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.modded.api.server.ServerProvider;
import top.offsetmonkey538.monkeylib538.neoforge.MonkeyLib538Initializer;

public final class ServerProviderImpl implements ServerProvider {

    @Override
    public @Nullable DedicatedServer getServerImpl() {
        return MonkeyLib538Initializer.getServer();
    }
}
