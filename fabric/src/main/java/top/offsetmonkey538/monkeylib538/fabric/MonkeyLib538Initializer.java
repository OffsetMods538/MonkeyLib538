package top.offsetmonkey538.monkeylib538.fabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.modded.impl.command.CommandRegistrationImpl;

public class MonkeyLib538Initializer implements ModInitializer, DedicatedServerModInitializer {
    private static @Nullable MinecraftDedicatedServer minecraftServer = null;

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
            CommandRegistrationImpl.commands.forEach(command -> dispatcher.register((LiteralArgumentBuilder<ServerCommandSource>) command));
        });
    }

    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer1 -> minecraftServer = (MinecraftDedicatedServer) minecraftServer1);
    }

    public static @Nullable MinecraftDedicatedServer getServer() {
        return minecraftServer;
    }
}
