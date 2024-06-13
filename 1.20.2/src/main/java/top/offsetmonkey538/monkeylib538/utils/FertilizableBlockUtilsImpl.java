package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FertilizableBlockUtilsImpl implements FertilizableBlockUtils {

    @Override
    public boolean isFertilizable(Fertilizable fertilizable, World world, BlockPos pos, BlockState state) {
        return fertilizable.isFertilizable(world, pos, state);
    }
}
