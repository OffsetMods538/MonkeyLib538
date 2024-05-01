package top.offsetmonkey538.monkeylib538.enchantment;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class EnchantmentUtilImpl implements EnchantmentUtil {
    @Override
    public void removeEnchantments(ItemStack itemStack, Enchantment... enchantments) {
        final ItemEnchantmentsComponent initialEnchantments = EnchantmentHelper.getEnchantments(itemStack);

        final ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(initialEnchantments);
        builder.remove(enchantment -> Arrays.stream(enchantments).anyMatch(enchantment1 -> enchantment.value() == enchantment1));

        EnchantmentHelper.set(itemStack, builder.build());
    }
}
