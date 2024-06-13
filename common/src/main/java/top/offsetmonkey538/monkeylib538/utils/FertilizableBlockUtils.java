package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface FertilizableBlockUtils {
    FertilizableBlockUtils INSTANCE = load(FertilizableBlockUtils.class);

    boolean isFertilizable(Fertilizable fertilizable, World world, BlockPos pos, BlockState state);
}
