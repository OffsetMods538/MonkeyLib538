package testmod.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import top.offsetmonkey538.monkeylib538.enchantment.EnchantmentUtil;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {

    @ModifyExpressionValue(
            method = "dropSelectedItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;dropSelectedItem(Z)Lnet/minecraft/item/ItemStack;"
            )
    )
    private ItemStack removeSharpnessFromItem(ItemStack original) {
        EnchantmentUtil.INSTANCE.removeEnchantments(original, Enchantments.SHARPNESS);
        return original;
    }
}
