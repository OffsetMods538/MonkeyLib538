package top.offsetmonkey538.monkeylib538.common.api.platform;

import org.jspecify.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.common.api.annotation.Internal;
import top.offsetmonkey538.monkeylib538.common.api.text.MonkeyLibText;

import java.nio.file.Path;
import java.util.function.Supplier;

import static top.offsetmonkey538.monkeylib538.common.MonkeyLib538Common.load;

/**
 * Utilities methods for working with different loaders
 */
public interface LoaderUtil {
    @Internal
    LoaderUtil INSTANCE = load(LoaderUtil.class);

    /**
     * Returns the current Minecraft version used.
     *
     * @return the current Minecraft version used
     */
    static String getMinecraftVersion() {
        return INSTANCE.getMinecraftVersionImpl();
    }

    /**
     * Provides the current mod loader's name/branding.
     *
     * <p>When running on client, it's fine to call this whenever.
     * <br>
     * When running on server, calling this before the SERVER_STARTING event has been invoked will return an empty string.</p>
     *
     * @return the current mod loader's name/branding or an empty string when called on a dedicated server before it has started starting.
     */
    static @Nullable String getLoaderName() {
        return BrandGetter.INSTANCE.getBrand();
    }

    /**
     * Returns the path to the config directory.
     *
     * @return the path to the config directory
     */
    static Path getConfigDir() {
        return INSTANCE.getConfigDirImpl();
    }
    /**
     * Returns the path to the mods directory.
     *
     * @return the path to the mods directory
     */
    static Path getModsDir() {
        return INSTANCE.getModsDirImpl();
    }

    /**
     * Returns true when the game is launched in a development environment (IDE).
     * <br>
     * On paper, this is only true when the jvm property {@code -DmonkeyLibDev=true} is added.
     *
     * @return true when the game is launched in a development environment (IDE).
     */
    static boolean isDevelopmentEnvironment() {
        return INSTANCE.isDevelopmentEnvironmentImpl();
    }

    /**
     * Returns true when the game is launched as a dedicated server.
     *
     * @return true when the game is launched as a dedicated server
     */
    static boolean isDedicatedServer() {
        return INSTANCE.isDedicatedServerImpl();
    }

    /**
     * Sends the messages supplied by the provided supplier to admins when they join.
     * <br>
     * Also sends the message(s) on singleplayer start
     *
     * @param messageSupplier called to get the messages to send
     */
    static void sendMessagesToAdminsOnJoin(final Supplier<MonkeyLibText[]> messageSupplier) {
        INSTANCE.sendMessagesToAdminsOnJoinImpl(messageSupplier);
    }

    @Internal String getMinecraftVersionImpl();
    @Internal Path getConfigDirImpl();
    @Internal Path getModsDirImpl();
    @Internal boolean isDevelopmentEnvironmentImpl();
    @Internal boolean isDedicatedServerImpl();
    @Internal void sendMessagesToAdminsOnJoinImpl(final Supplier<MonkeyLibText[]> messageSupplier);

    @Internal
    interface BrandGetter {
        BrandGetter INSTANCE = isDedicatedServer() ? load(ServerBrandGetter.class) : load(ClientBrandGetter.class);

        @Nullable String getBrand();
    }

    @Internal
    interface ClientBrandGetter extends BrandGetter {

    }
    @Internal
    interface ServerBrandGetter extends BrandGetter {

    }
}
