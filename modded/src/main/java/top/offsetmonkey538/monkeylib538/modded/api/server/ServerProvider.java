package top.offsetmonkey538.monkeylib538.modded.api.server;

import org.jspecify.annotations.Nullable;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

import net.minecraft.server.dedicated.DedicatedServer;

public interface ServerProvider {
    ServerProvider INSTANCE = load(ServerProvider.class);

    /**
     * Server will only exist after server starting event!
     * @return the current minecraft dedicated server.
     */
    static @Nullable DedicatedServer getServer() {
        return INSTANCE.getServerImpl();
    }

    @Nullable DedicatedServer getServerImpl();
}
