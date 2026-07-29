package sircow.torrential.mixin;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sircow.torrential.potion.ModPotions;

@Mixin(PotionBrewing.class)
public class PotionBrewingMixin {
    @Inject(method = "addVanillaMixes", at = @At("HEAD"), cancellable = true)
    private static void torrential$addVanillaMixes(PotionBrewing.Builder builder, CallbackInfo ci) {
        builder.addMix(Potions.WATER, Items.RABBIT_HIDE, ModPotions.LUCK);
        builder.addMix(ModPotions.LUCK, Items.REDSTONE, ModPotions.LONG_LUCK);
        builder.addMix(ModPotions.LUCK, Items.GLOWSTONE_DUST, ModPotions.STRONG_LUCK);
        builder.addMix(Potions.WATER, Items.NAUTILUS_SHELL, ModPotions.NAUTILUS_BLESSING);
        ci.cancel();
    }
}
