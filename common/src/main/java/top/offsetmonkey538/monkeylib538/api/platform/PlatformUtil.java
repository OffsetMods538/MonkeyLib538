package top.offsetmonkey538.monkeylib538.api.platform;

import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.api.text.MonkeyLibText;

import java.nio.file.Path;
import java.util.function.Supplier;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface PlatformUtil {
    PlatformUtil INSTANCE = load(PlatformUtil.class);
    Supplier<String> BRANDING_SUPPLIER = isDedicatedServer() ? load(ServerBrandGetter.class) : load(ClientBrandGetter.class);

    static String getMinecraftVersion() {
        return INSTANCE.getMinecraftVersionImpl();
    }

    /**
     * Provides the current mod loader's name/branding.
     * <p>
     * When running on client, it's fine to call this whenever.
     * <br>
     * When running on server, calling this before the SERVER_STARTING event has been invoked will return an empty string.
     * </p>
     *
     * @return the current mod loader's name/branding or an empty string when called on a dedicated server before it has started starting.
     */
    static @Nullable String getLoaderName() {
        return BRANDING_SUPPLIER.get();
    }

    static Path getConfigDir() {
        return INSTANCE.getConfigDirImpl();
    }
    static Path getModsDir() {
        return INSTANCE.getModsDirImpl();
    }
    static Path getGameDir() {
        return INSTANCE.getGameDirImpl();
    }

    static boolean isDevelopmentEnvironment() {
        return INSTANCE.isDevelopmentEnvironmentImpl();
    }

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

    String getMinecraftVersionImpl();
    Path getConfigDirImpl();
    Path getModsDirImpl();
    Path getGameDirImpl();
    boolean isDevelopmentEnvironmentImpl();
    boolean isDedicatedServerImpl();
    void sendMessagesToAdminsOnJoinImpl(final Supplier<MonkeyLibText[]> messageSupplier);

    interface ClientBrandGetter extends Supplier<String> {

    }

    interface ServerBrandGetter extends Supplier<String> {

    }
}
