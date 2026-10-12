package sircow.torrential.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sircow.torrential.component.ModComponents;
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

    // migrate old rods
    @Unique private boolean torrential$checkedLegacyRods = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void torrential$migrateLegacyRodsOnJoin(CallbackInfo ci) {
        if (torrential$checkedLegacyRods) return;
        torrential$checkedLegacyRods = true;

        ServerPlayer self = (ServerPlayer) (Object) this;
        Inventory inventory = self.getInventory();
        boolean changed = false;

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !stack.is(Items.FISHING_ROD)) continue;
            changed |= ModComponents.migrateRod(stack);
        }

        if (changed) {
            inventory.setChanged();
            ModComponents.sendMigrationMessage(self);
        }
    }
}
