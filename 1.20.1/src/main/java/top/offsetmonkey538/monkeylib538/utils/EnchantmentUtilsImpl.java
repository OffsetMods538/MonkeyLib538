package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Map;

public class EnchantmentUtilsImpl implements EnchantmentUtils {
    @Override
    public int getLevel(Identifier enchantment, World world, ItemStack stack) {
        return EnchantmentHelper.getLevel(getEnchantment(enchantment, world.getRegistryManager()), stack);
    }

    @Override
    public void removeEnchantment(Identifier enchantment, World world, ItemStack stack) {
        final Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);

        enchantments.remove(getEnchantment(enchantment, world.getRegistryManager()));

        EnchantmentHelper.set(enchantments, stack);
    }

    private Enchantment getEnchantment(Identifier enchantment, DynamicRegistryManager registryManager) {
        final RegistryKey<Registry<Enchantment>> enchantmentRegistry = RegistryKeys.ENCHANTMENT;
        return registryManager.get(enchantmentRegistry).getEntry(RegistryKey.of(enchantmentRegistry, enchantment)).orElseThrow().value();
    }
}
