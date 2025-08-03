package top.offsetmonkey538.monkeylib538;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import top.offsetmonkey538.monkeylib538.impl.command.CommandRegistrationImpl;

public class MonkeyLib538Initializer implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            if (CommandRegistrationImpl.commands == null) throw new IllegalStateException("The CommandRegistrationCallback was called twice???????");

            //noinspection unchecked
            CommandRegistrationImpl.commands.forEach(command -> dispatcher.register((LiteralArgumentBuilder<ServerCommandSource>) command));
            CommandRegistrationImpl.commands = null;
        });
    }
}
