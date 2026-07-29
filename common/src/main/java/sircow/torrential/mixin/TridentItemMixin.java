package sircow.torrential.mixin;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    // allow riptide to be used outside of rain or touching water while having conduit power effect
    @Redirect(method = {"releaseUsing", "use"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    private boolean torrential$replaceWaterCheck(Player playerEntity) {
        if (playerEntity.hasEffect(MobEffects.CONDUIT_POWER) || playerEntity.isInWaterOrRain()) return true;
        else if (!playerEntity.hasEffect(MobEffects.CONDUIT_POWER) && !playerEntity.isInWaterOrRain()) return false;
        return false;
    }
}
