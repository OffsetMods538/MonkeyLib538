package top.offsetmonkey538.monkeylib538.neoforge;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.jetbrains.annotations.ApiStatus;
import top.offsetmonkey538.monkeylib538.api.command.CommandRegistrationApi;
import top.offsetmonkey538.monkeylib538.neoforge.impl.command.CommandRegistrationImpl;

/**
 * Initializer of the neoforge platform monkeylib538
 * <br />
 * Registers commands added in {@link CommandRegistrationApi}.
 */
@ApiStatus.Internal
@Mod("monkeylib538_common_neoforge")
public class MonkeyLib538Initializer {
    /**
     * Constructor that neo magic calls
     */
    @ApiStatus.Internal
    public MonkeyLib538Initializer(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(RegisterCommandsEvent.class, registerCommandsEvent -> {
            //noinspection unchecked
            CommandRegistrationImpl.commands.forEach(command -> registerCommandsEvent.getDispatcher().register((LiteralArgumentBuilder<CommandSourceStack>) command));
        });
    }
}
