package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RecipeManagerUtilsImpl implements RecipeManagerUtils {
    private static final RecipeManager.MatchGetter<SingleStackRecipeInput, SmeltingRecipe> CACHED_MATCH_GETTER = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);

    @Override
    public @Nullable ItemStack getSmeltingResult(ItemStack input, World world) {
        final Optional<RecipeEntry<SmeltingRecipe>> optionalRecipeEntry = CACHED_MATCH_GETTER.getFirstMatch(new SingleStackRecipeInput(input), world);

        if (optionalRecipeEntry.isEmpty()) return null;
        final RecipeEntry<SmeltingRecipe> recipeEntry = optionalRecipeEntry.get();

        return recipeEntry.value().getResult(world.getRegistryManager());
    }
}
