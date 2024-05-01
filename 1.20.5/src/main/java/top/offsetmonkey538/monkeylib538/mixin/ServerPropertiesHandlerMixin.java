package top.offsetmonkey538.monkeylib538.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.ServerPropertiesHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import top.offsetmonkey538.monkeylib538.duck.ServerPropertiesHandlerDuck;

import java.util.Optional;

@Mixin(ServerPropertiesHandler.class)
public abstract class ServerPropertiesHandlerMixin implements ServerPropertiesHandlerDuck {

    @Shadow
    private static Optional<MinecraftServer.ServerResourcePackProperties> getServerResourcePackProperties(String id, String url, String sha1, @Nullable String hash, boolean required, String prompt) {
        return Optional.empty();
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Mutable
    @Shadow @Final public Optional<MinecraftServer.ServerResourcePackProperties> serverResourcePackProperties;

    @Override
    public void monkeylib538$setResourcePackProperties(String url, String sha1Hash) {
        final ServerPropertiesHandler thiz = ((ServerPropertiesHandler) (Object) this);

        this.serverResourcePackProperties = getServerResourcePackProperties(
                thiz.getString("resource-pack-id", ""),
                url,
                sha1Hash,
                null,
                thiz.parseBoolean("require-resource-pack", false),
                thiz.getString("resource-pack-prompt", "")
        );
    }
}
