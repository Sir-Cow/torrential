package sircow.torrential.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sircow.torrential.damage.ModDamageTypes;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin {
    // prevent advancements where player needs to kill a mob from granting when killed by conduit
    @Inject(method = "awardKillScore", at = @At("HEAD"), cancellable = true)
    private void torrential$preventAdvancementStatIncrease(Entity victim, DamageSource killingBlow, CallbackInfo ci) {
        if (killingBlow.is(ModDamageTypes.CONDUIT)) {
            ci.cancel();
        }
    }
}
