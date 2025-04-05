package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RecipeManagerUtilsImpl implements RecipeManagerUtils {
    private static final ServerRecipeManager.MatchGetter<SingleStackRecipeInput, SmeltingRecipe> CACHED_MATCH_GETTER = ServerRecipeManager.createCachedMatchGetter(RecipeType.SMELTING);

    @Override
    public @Nullable ItemStack getSmeltingResult(ItemStack input, World world) {
        if (!(world instanceof ServerWorld serverWorld)) {
            return null;
        }

        final Optional<RecipeEntry<SmeltingRecipe>> optionalRecipeEntry = CACHED_MATCH_GETTER.getFirstMatch(new SingleStackRecipeInput(input), serverWorld);

        if (optionalRecipeEntry.isEmpty()) return null;
        final RecipeEntry<SmeltingRecipe> recipeEntry = optionalRecipeEntry.get();

        return recipeEntry.value().result();
    }
}