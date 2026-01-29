package top.offsetmonkey538.monkeylib538.modded.v1211.impl.command;

import net.minecraft.commands.CommandSourceStack;
import top.offsetmonkey538.monkeylib538.modded.api.command.ModdedCommandAbstractionApi;

public final class ModdedCommandAbstractionApiVersionSpecificImpl implements ModdedCommandAbstractionApi.VersionSpecific {
    @Override
    public boolean isOp(CommandSourceStack source) {
        return source.hasPermission(source.getServer().getOperatorUserPermissionLevel());
    }
}
