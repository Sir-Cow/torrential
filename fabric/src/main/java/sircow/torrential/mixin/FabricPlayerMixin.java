package sircow.torrential.mixin;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public class FabricPlayerMixin {
    // conduit haste bonus
    @ModifyVariable(method = "getDestroySpeed", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float torrential$applyConduitModifier(float speed, BlockState state) {
        Player self = (Player) (Object) this;
        MobEffectInstance conduit = self.getEffect(MobEffects.CONDUIT_POWER);
        if (conduit == null) return speed;

        float conduitModifier = (conduit.getAmplifier() + 1) * 0.05F;

        if (MobEffectUtil.hasDigSpeed(self)) {
            float hasteBonus = (MobEffectUtil.getDigSpeedAmplification(self) + 1) * 0.2F;
            speed /= 1.0F + hasteBonus;
            speed *= 1.0F + hasteBonus + conduitModifier;
        }
        else speed *= 1.0F + conduitModifier;
        return speed;
    }
}
