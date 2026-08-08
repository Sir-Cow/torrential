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
public class FabricPotionBrewingMixin {
    @Inject(method = "addVanillaMixes", at = @At("TAIL"))
    private static void torrential$addVanillaMixes(PotionBrewing.Builder builder, CallbackInfo ci) {
        builder.addMix(Potions.AWKWARD, Items.RABBIT_HIDE, ModPotions.luckHolder());
        builder.addMix(ModPotions.luckHolder(), Items.REDSTONE, ModPotions.longLuckHolder());
        builder.addMix(ModPotions.luckHolder(), Items.GLOWSTONE_DUST, ModPotions.strongLuckHolder());
    }
}
