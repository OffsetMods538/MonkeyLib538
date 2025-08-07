package top.offsetmonkey538.monkeylib538.fabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.ApiStatus;
import top.offsetmonkey538.monkeylib538.api.command.CommandRegistrationApi;
import top.offsetmonkey538.monkeylib538.fabric.impl.command.CommandRegistrationImpl;

/**
 * Initializer of the fabric platform monkeylib538
 * <br />
 * Registers commands added in {@link CommandRegistrationApi}.
 */
@ApiStatus.Internal
public class MonkeyLib538Initializer implements ModInitializer {
    /**
     * Public no-args constructor for fabric to do it's magic with
     */
    @ApiStatus.Internal
    public MonkeyLib538Initializer() {

    }

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
