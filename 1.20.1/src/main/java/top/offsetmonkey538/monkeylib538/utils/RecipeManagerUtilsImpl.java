package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RecipeManagerUtilsImpl implements RecipeManagerUtils {
    private static final RecipeManager.MatchGetter<Inventory, SmeltingRecipe> CACHED_MATCH_GETTER = RecipeManager.createCachedMatchGetter(RecipeType.SMELTING);

    @Override
    public @Nullable ItemStack getSmeltingResult(ItemStack input, World world) {
        final Inventory inputInv = new SimpleInventory(input);
        final Optional<SmeltingRecipe> optionalRecipeEntry = CACHED_MATCH_GETTER.getFirstMatch(inputInv, world);

        if (optionalRecipeEntry.isEmpty()) return null;
        final SmeltingRecipe recipeEntry = optionalRecipeEntry.get();

        return recipeEntry.craft(inputInv, world.getRegistryManager());
    }
}
