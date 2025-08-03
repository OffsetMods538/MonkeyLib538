package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.impl.command.ConfigCommandImpl;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;

public interface ConfigCommandApi {
    ConfigCommandApi INSTANCE = new ConfigCommandImpl();

    void registerConfigCommand(final @NotNull ConfigHolder<?> configHolder, final @NotNull String... commandTree);

    /**
     * Creates a config command using the provided name and {@link ConfigHolder}.
     * <p>
     * The generated command will have the following subcommands:
     * <pre>
     *     <code>reset</code>:
     *         Resets the config to default values
     *     <code>reload</code>:
     *         Reloads the config from disk
     *     <code>get</code>:
     *         <code>option</code>:
     *             Gets the given config options value.
     *     <code>set</code>:
     *         <code>option</code>:
     *             If there's no way to represent the config option in a command,
     *             this will tell the user to modify it manually.
     *             <code>newValue</code>:
     *                 Sets the given config option to the given new value.
     * </pre>
     * <p>
     *     If you only have one config and no other commands, you can directly register the command like this:
     *     <pre>
     *     {@code
     *     CommandRegistrationCallback.EVENT.register((...) -> {
     *         dispatcher.register(ConfigCommandApi.INSTANCE.createConfigCommand(MOD_ID, configHolder));
     *     });
     *     }
     *     </pre>
     *     if you already have a command (and/or have multiple configs), you could add to it like this:
     *     <pre>
     *     {@code
     *     CommandRegistrationCallback.EVENT.register((...) -> {
     *         LiteralArgumentBuilder<ServerCommandSource> command = literal(MOD_ID);
     *
     *         // ... adding other commands
     *
     *         // Will be invoked like /modId config reset/reload/get/set
     *         command.then(ConfigCommandApi.INSTANCE.createConfigCommand("config", configHolder));
     *
     *         dispatcher.register(command);
     *     });
     *     }
     *     </pre>
     *
     * @param name the name of the command.
     * @param configHolder your {@link ConfigHolder} instance.
     * @return a config command using the provided name and {@link ConfigHolder}.
     */
    @NotNull LiteralArgumentBuilder<?> createConfigCommand(final @NotNull String name, final @NotNull ConfigHolder<?> configHolder);
}
