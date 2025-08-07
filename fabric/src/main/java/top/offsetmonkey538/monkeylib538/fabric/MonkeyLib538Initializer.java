package top.offsetmonkey538.monkeylib538.fabric;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.ApiStatus;
import top.offsetmonkey538.monkeylib538.api.command.CommandRegistrationApi;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.api.text.TextFormattingApi;
import top.offsetmonkey538.monkeylib538.fabric.api.text.FabricMonkeyLibText;
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
        ServerPlayConnectionEvents.JOIN.register((serverPlayNetworkHandler, packetSender, minecraftServer) -> {
            try {
                final String text = "&1Please click &{hoverText,'&2Cli\\'k meh','&3Right &{runCommand,'&4/command','&5Over'} <- There!'}aaa";
                //final String text = "Please click&{hoverText,'Cli\\'k meh','Right here'}and not here!!";
                final MonkeyLibText result = TextFormattingApi.styleText(text);

                serverPlayNetworkHandler.player.sendMessage(FabricMonkeyLibText.of(result).getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            if (CommandRegistrationImpl.commands == null) throw new IllegalStateException("The CommandRegistrationCallback was called twice???????");

            //noinspection unchecked
            CommandRegistrationImpl.commands.forEach(command -> dispatcher.register((LiteralArgumentBuilder<ServerCommandSource>) command));
            CommandRegistrationImpl.commands = null;
        });
    }
}
