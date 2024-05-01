package top.offsetmonkey538.monkeylib538.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class EnchantmentUtilImpl implements EnchantmentUtil {

    @Override
    public void removeEnchantments(ItemStack itemStack, Enchantment... enchantmentsToRemove) {
        final Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(itemStack);

        for (Enchantment enchantment : enchantmentsToRemove) {
            enchantments.remove(enchantment);
        }

        EnchantmentHelper.set(enchantments, itemStack);
    }
}
