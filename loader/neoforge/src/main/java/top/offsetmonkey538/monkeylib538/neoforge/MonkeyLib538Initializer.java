package top.offsetmonkey538.monkeylib538.neoforge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.dedicated.DedicatedServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ServerLifecycleApi;
import top.offsetmonkey538.monkeylib538.modded.impl.command.CommandRegistrationImpl;

@Mod("monkeylib538")
public class MonkeyLib538Initializer {
    private static @Nullable DedicatedServer minecraftServer = null;

    /**
     * Constructor that neo magic calls
     */
    public MonkeyLib538Initializer(IEventBus modEventBus, ModContainer modContainer) {
        MonkeyLib538Common.initialize();

        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, serverStartingEvent -> minecraftServer = (serverStartingEvent.getServer() instanceof DedicatedServer server ? server : null));
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, registerCommandsEvent -> {
            //noinspection unchecked
            CommandRegistrationImpl.commands.forEach(command -> registerCommandsEvent.getDispatcher().register((LiteralArgumentBuilder<CommandSourceStack>) command));
        });

        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, event -> ServerLifecycleApi.STARTING.getInvoker().run());
        NeoForge.EVENT_BUS.addListener(ServerStartedEvent.class, event -> ServerLifecycleApi.STARTED.getInvoker().run());
        NeoForge.EVENT_BUS.addListener(ServerStoppingEvent.class, event -> ServerLifecycleApi.STOPPING.getInvoker().run());
        NeoForge.EVENT_BUS.addListener(ServerStoppedEvent.class, event -> ServerLifecycleApi.STOPPED.getInvoker().run());
    }

    public static @Nullable DedicatedServer getServer() {
        return minecraftServer;
    }
}
