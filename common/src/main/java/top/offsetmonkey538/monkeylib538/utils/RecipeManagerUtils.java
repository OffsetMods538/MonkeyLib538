package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface RecipeManagerUtils {
    RecipeManagerUtils INSTANCE = load(RecipeManagerUtils.class);

    @Nullable
    ItemStack getSmeltingResult(ItemStack input, World world);
}
