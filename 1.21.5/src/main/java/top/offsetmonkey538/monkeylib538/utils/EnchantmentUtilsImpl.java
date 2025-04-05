package top.offsetmonkey538.monkeylib538.utils;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class EnchantmentUtilsImpl implements EnchantmentUtils {
    @Override
    public int getLevel(Identifier enchantment, World world, ItemStack stack) {
        return EnchantmentHelper.getLevel(getEnchantment(enchantment, world.getRegistryManager()), stack);
    }

    @Override
    public void removeEnchantment(Identifier enchantment, World world, ItemStack stack) {
        final ItemEnchantmentsComponent enchantments = EnchantmentHelper.getEnchantments(stack);

        final ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(enchantments);
        builder.remove(enchantment1 -> enchantment1.equals(getEnchantment(enchantment, world.getRegistryManager())));

        EnchantmentHelper.set(stack, builder.build());
    }

    private RegistryEntry<Enchantment> getEnchantment(Identifier enchantment, DynamicRegistryManager registryManager) {
        final RegistryKey<Registry<Enchantment>> enchantmentRegistry = RegistryKeys.ENCHANTMENT;
        return registryManager.getOrThrow(enchantmentRegistry).getEntry(enchantment).orElseThrow();
    }
}
