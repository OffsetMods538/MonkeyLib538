package top.offsetmonkey538.monkeylib538.modded.v12111.impl.command;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.Permissions;
import top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi;

public final class ModdedCommandAbstractionApiVersionSpecificImpl implements ModdedCommandAbstractionApi.VersionSpecific {
    @Override
    public boolean isOp(CommandSourceStack source) {
        return source.permissions().hasPermission(Permissions.COMMANDS_ADMIN);
    }
}
