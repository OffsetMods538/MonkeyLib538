package top.offsetmonkey538.monkeylib538.modded.api.server;

import org.jspecify.annotations.Nullable;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

import net.minecraft.server.dedicated.DedicatedServer;
import top.offsetmonkey538.offsetutils538.api.annotation.Internal;

public interface ServerProvider {
    @Internal
    ServerProvider INSTANCE = load(ServerProvider.class);

    /**
     * Server will only exist after server starting event!
     * @return the current minecraft dedicated server.
     */
    static @Nullable DedicatedServer getServer() {
        return INSTANCE.getServerImpl();
    }

    @Internal @Nullable DedicatedServer getServerImpl();
}
