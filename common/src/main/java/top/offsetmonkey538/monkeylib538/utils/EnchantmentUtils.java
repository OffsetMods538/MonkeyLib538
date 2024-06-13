package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface EnchantmentUtils {
    EnchantmentUtils INSTANCE = load(EnchantmentUtils.class);

    int getLevel(Identifier enchantment, World world, ItemStack stack);
    void removeEnchantment(Identifier enchantment, World world, ItemStack stack);

    default int getLevel(String enchantment, World world, ItemStack stack) {
        return getLevel(IdentifierUtils.INSTANCE.of(enchantment), world, stack);
    }
    default void removeEnchantment(String enchantment, World world, ItemStack stack) {
        removeEnchantment(IdentifierUtils.INSTANCE.of(enchantment), world, stack);
    }
}
