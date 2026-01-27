package top.offsetmonkey538.monkeylib538.fabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.dedicated.DedicatedServer;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.common.api.lifecycle.ServerLifecycleApi;
import top.offsetmonkey538.monkeylib538.modded.impl.command.CommandRegistrationImpl;

public class MonkeyLib538Initializer implements ModInitializer {
    private static @Nullable DedicatedServer minecraftServer = null;

    /**
     * Public no-args constructor for fabric to do it's magic with
     */
    public MonkeyLib538Initializer() {

    }

    @Override
    public void onInitialize() {
        MonkeyLib538Common.initialize();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            //noinspection unchecked
            CommandRegistrationImpl.commands.forEach(command -> dispatcher.register((LiteralArgumentBuilder<CommandSourceStack>) command));
        });

        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer1 -> {
            minecraftServer = (minecraftServer1 instanceof DedicatedServer server ? server : null);
            ServerLifecycleApi.STARTING.getInvoker().run();
        });
        ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> ServerLifecycleApi.STARTED.getInvoker().run());
        ServerLifecycleEvents.SERVER_STOPPING.register(minecraftServer -> ServerLifecycleApi.STOPPING.getInvoker().run());
        ServerLifecycleEvents.SERVER_STOPPED.register(minecraftServer -> ServerLifecycleApi.STOPPED.getInvoker().run());
    }

    public static @Nullable DedicatedServer getServer() {
        return minecraftServer;
    }
}
