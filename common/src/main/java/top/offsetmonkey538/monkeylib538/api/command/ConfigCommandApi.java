package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.NotNull;
import top.offsetmonkey538.monkeylib538.impl.command.ConfigCommandImpl;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;

/**
 * Provides methods for creating and registering config commands using {@link ConfigHolder}s.
 * <p>
 * A generated config command will have the following subcommands:
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
 */
public interface ConfigCommandApi {
    /**
     * The instance
     */
    ConfigCommandApi INSTANCE = new ConfigCommandImpl();

    /**
     * Creates and registers a config command using the provided command tree and {@link ConfigHolder}.
     * <p>
     *     The command tree could for example be {@code "modid", "config"} for the command to look like {@code /modid config reset/reload/get/set}
     * </p>
     *
     * @param configHolder your {@link ConfigHolder} instance.
     * @param commandTree the command tree.
     */
    void registerConfigCommand(final @NotNull ConfigHolder<?> configHolder, final @NotNull String... commandTree);

    /**
     * Creates a config command using the provided name and {@link ConfigHolder}.
     *
     * @param name the name of the command.
     * @param configHolder your {@link ConfigHolder} instance.
     * @return a config command using the provided name and {@link ConfigHolder}.
     */
    @NotNull LiteralArgumentBuilder<?> createConfigCommand(final @NotNull String name, final @NotNull ConfigHolder<?> configHolder);
}
