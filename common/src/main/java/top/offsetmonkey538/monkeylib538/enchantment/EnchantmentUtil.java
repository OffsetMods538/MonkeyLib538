package top.offsetmonkey538.monkeylib538.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

import static top.offsetmonkey538.monkeylib538.MonkeyLib538Common.load;

public interface EnchantmentUtil {
    EnchantmentUtil INSTANCE = load(EnchantmentUtil.class);

    void removeEnchantments(ItemStack itemStack, Enchantment... enchantmentsToRemove);
}
