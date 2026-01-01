package top.offsetmonkey538.monkeylib538.neoforge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.modded.impl.command.CommandRegistrationImpl;

@Mod("monkeylib538")
public class MonkeyLib538Initializer {
    private static @Nullable DedicatedServer minecraftServer = null;

    /**
     * Constructor that neo magic calls
     */
    public MonkeyLib538Initializer(IEventBus modEventBus, ModContainer modContainer) {
        MonkeyLib538Common.initialize();

        NeoForge.EVENT_BUS.addListener(ServerStartingEvent.class, serverStartingEvent -> minecraftServer = (DedicatedServer) serverStartingEvent.getServer());
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, registerCommandsEvent -> {
            //noinspection unchecked
            CommandRegistrationImpl.commands.forEach(command -> registerCommandsEvent.getDispatcher().register((LiteralArgumentBuilder<CommandSourceStack>) command));
        });
    }

    public static @Nullable DedicatedServer getServer() {
        return minecraftServer;
    }
}
