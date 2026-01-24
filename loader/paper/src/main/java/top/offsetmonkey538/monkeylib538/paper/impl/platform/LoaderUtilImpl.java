package top.offsetmonkey538.monkeylib538.paper.impl.platform;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common;
import top.offsetmonkey538.monkeylib538.common.api.platform.LoaderUtil;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;
import top.offsetmonkey538.monkeylib538.paper.api.text.PaperMonkeyLibText;
import top.offsetmonkey538.monkeylib538.paper.impl.command.CommandRegistrationImpl;
import top.offsetmonkey538.monkeylib538.paper.impl.lifecycle.ServerLifecycleApiImpl;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class LoaderUtilImpl implements LoaderUtil {
    private static @Nullable MonkeyLib538Initializer plugin;

    @Override
    public String getMinecraftVersionImpl() {
        return Bukkit.getMinecraftVersion();
    }

    @Override
    public Path getConfigDirImpl() {
        return getPlugin().getDataPath().getParent();
    }

    @Override
    public Path getModsDirImpl() {
        return Bukkit.getPluginsFolder().toPath();
    }

    @Override
    public boolean isDevelopmentEnvironmentImpl() {
        return Boolean.getBoolean("xyz.jpenilla.run-task");
    }

    @Override
    public boolean isDedicatedServerImpl() {
        return true;
    }

    @Override
    public void sendMessagesToAdminsOnJoinImpl(Supplier<MonkeyLibText[]> messageSupplier) {
        Bukkit.getPluginManager().registerEvents(new AdminMessageSenderEventHandler(messageSupplier), getPlugin());
    }

    private record AdminMessageSenderEventHandler(Supplier<MonkeyLibText[]> messageSupplier) implements Listener {
        @EventHandler(priority = EventPriority.MONITOR)
        public void onPlayerJoin(final PlayerJoinEvent event) {
            if (!event.getPlayer().isOp()) return;

            for (MonkeyLibText text : messageSupplier.get()) event.getPlayer().sendMessage(PaperMonkeyLibText.of(text).getText());
        }
    }

    private static void setPlugin(MonkeyLib538Initializer plugin) {
        LoaderUtilImpl.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        if (plugin == null) throw new IllegalStateException("Tried accessing plugin before monkeylib538 was initialized");
        return plugin;
    }



    public static final class MonkeyLib538Initializer extends JavaPlugin {
        @Override
        public void onEnable() { // TODO: only runs once? Sure do hope so
            setPlugin(this);
            MonkeyLib538Common.initialize();
            this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
                for (LiteralArgumentBuilder<?> command : CommandRegistrationImpl.commands) {
                    //noinspection unchecked
                    commands.registrar().register((LiteralCommandNode<CommandSourceStack>) command.build());
                }
            });
        }

        @Override
        public void onDisable() {
            ServerLifecycleApiImpl.STOPPING.getInvoker().run();
            ServerLifecycleApiImpl.STOPPED.getInvoker().run();
        }
    }
}
