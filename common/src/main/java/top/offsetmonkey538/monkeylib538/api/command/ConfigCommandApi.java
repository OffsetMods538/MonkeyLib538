package top.offsetmonkey538.monkeylib538.api.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.offsetmonkey538.monkeylib538.impl.command.ConfigCommandImpl;
import top.offsetmonkey538.offsetconfig538.api.config.ConfigHolder;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

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
@ApiStatus.NonExtendable
public interface ConfigCommandApi {
    /**
     * The instance
     */
    @ApiStatus.Internal
    ConfigCommandApi INSTANCE = load(ConfigCommandApi.class);

    /**
     * Creates and registers a config command using the provided command tree and {@link ConfigHolder}.
     * <p>
     *     The command tree could for example be {@code "modid", "config"} for the command to look like {@code /modid config reset/reload/get/set}
     * </p>
     *
     * @param configHolder your {@link ConfigHolder} instance.
     * @param commandTree the command tree.
     */
    static void registerConfigCommand(final @NotNull ConfigHolder<?> configHolder, final @NotNull String... commandTree) {
        registerConfigCommand(configHolder, null, commandTree);
    }
    /**
     * Creates and registers a config command using the provided command tree and {@link ConfigHolder}.
     * <br/>
     * The {@code configReloadCallback} is executed after the {@code reload} command is run, and can be used for reinitializing stuff based on the new values.
     * <p>
     *     The command tree could for example be {@code "modid", "config"} for the command to look like {@code /modid config reset/reload/get/set}
     * </p>
     *
     * @param configHolder your {@link ConfigHolder} instance.
     * @param configReloadCallback executed when config is reloaded by the {@code reload} command.
     * @param commandTree the command tree.
     */
    static void registerConfigCommand(final @NotNull ConfigHolder<?> configHolder, final @Nullable Runnable configReloadCallback, final @NotNull String... commandTree) {
        registerConfigCommand(configHolder, configReloadCallback, null, commandTree);
    }
    /**
     * Creates and registers a config command using the provided command tree and {@link ConfigHolder}.
     * <br/>
     * The {@code configReloadCallback} is executed after the {@code reload} command is run, and can be used for reinitializing stuff based on the new values.
     * <p>
     *     The command tree could for example be {@code "modid", "config"} for the command to look like {@code /modid config reset/reload/get/set}
     * </p>
     *
     * @param configHolder your {@link ConfigHolder} instance.
     * @param configReloadCallback executed when config is reloaded by the {@code reload} command.
     * @param configValueSetCallback executed when a config value is changed by the {@code set} command.
     * @param commandTree the command tree.
     */
    static void registerConfigCommand(final @NotNull ConfigHolder<?> configHolder, final @Nullable Runnable configReloadCallback, final @Nullable Runnable configValueSetCallback, final @NotNull String... commandTree) {
        INSTANCE.registerConfigCommandImpl(configHolder, configReloadCallback, configValueSetCallback, commandTree);
    }

    /**
     * Creates a config command using the provided name and {@link ConfigHolder}.
     *
     * @param commandName the name of the command.
     * @param configHolder your {@link ConfigHolder} instance.
     * @return a config command using the provided name and {@link ConfigHolder}.
     */
    static @NotNull LiteralArgumentBuilder<?> createConfigCommand(final @NotNull String commandName, final @NotNull ConfigHolder<?> configHolder) {
        return createConfigCommand(commandName, configHolder, null);
    }
    /**
     * Creates a config command using the provided name and {@link ConfigHolder}.
     * <br/>
     * The {@code configReloadCallback} is executed after the {@code reload} command is run, and can be used for reinitializing stuff based on the new values.
     *
     * @param commandName the name of the command.
     * @param configReloadCallback executed when config is reloaded by the {@code reload} command.
     * @param configHolder your {@link ConfigHolder} instance.
     * @return a config command using the provided name and {@link ConfigHolder}.
     */
    static @NotNull LiteralArgumentBuilder<?> createConfigCommand(final @NotNull String commandName, final @NotNull ConfigHolder<?> configHolder, final @Nullable Runnable configReloadCallback) {
        return createConfigCommand(commandName, configHolder, configReloadCallback, null);
    }
    /**
     * Creates a config command using the provided name and {@link ConfigHolder}.
     * <br/>
     * The {@code configReloadCallback} is executed after the {@code reload} command is run, and can be used for reinitializing stuff based on the new values.
     *
     * @param commandName the name of the command.
     * @param configReloadCallback executed when config is reloaded by the {@code reload} command.
     * @param configValueSetCallback executed when a config value is changed by the {@code set} command.
     * @param configHolder your {@link ConfigHolder} instance.
     * @return a config command using the provided name and {@link ConfigHolder}.
     */
    static @NotNull LiteralArgumentBuilder<?> createConfigCommand(final @NotNull String commandName, final @NotNull ConfigHolder<?> configHolder, final @Nullable Runnable configReloadCallback, final @Nullable Runnable configValueSetCallback) {
        return INSTANCE.createConfigCommandImpl(commandName, configHolder, configReloadCallback, configValueSetCallback);
    }


    /**
     * Implementation of {@link #registerConfigCommand(ConfigHolder, Runnable, String...)}.
     *
     * @param configHolder your {@link ConfigHolder} instance.
     * @param configReloadCallback executed when config is reloaded by the {@code reload} command.
     * @param configValueSetCallback executed when a config value is changed by the {@code set} command.
     * @param commandTree the command tree.
     */
    @ApiStatus.Internal
    void registerConfigCommandImpl(final @NotNull ConfigHolder<?> configHolder, final @Nullable Runnable configReloadCallback, final @Nullable Runnable configValueSetCallback, final @NotNull String... commandTree);

    /**
     * Implementation of {@link #createConfigCommand(String, ConfigHolder, Runnable)}.
     *
     * @param commandName the name of the command.
     * @param configReloadCallback executed when config is reloaded by the {@code reload} command.
     * @param configValueSetCallback executed when a config value is changed by the {@code set} command.
     * @param configHolder your {@link ConfigHolder} instance.
     * @return a config command using the provided name and {@link ConfigHolder}.
     */
    @ApiStatus.Internal
    @NotNull LiteralArgumentBuilder<?> createConfigCommandImpl(final @NotNull String commandName, final @NotNull ConfigHolder<?> configHolder, final @Nullable Runnable configReloadCallback, final @Nullable Runnable configValueSetCallback);
}
