package sircow.torrential.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sircow.torrential.component.ModComponents;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Unique private long torrential$lastMigrationMessageTime = 0L;

    @Inject(method = "add(ILnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"))
    private void torrential$migrateLegacyRodOnAdd(int slot, ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        torrential$tryMigrate(itemStack);
    }

    @Inject(method = "setItem(ILnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"))
    private void torrential$migrateLegacyRodOnSetItem(int slot, ItemStack itemStack, CallbackInfo ci) {
        torrential$tryMigrate(itemStack);
    }

    @Unique
    private void torrential$tryMigrate(ItemStack stack) {
        if (stack.isEmpty() || !stack.is(Items.FISHING_ROD)) return;
        if (!ModComponents.hasLegacyComponents(stack)) return;
        if (!ModComponents.migrateRod(stack)) return;

        Inventory self = (Inventory) (Object) this;
        self.setChanged();

        if (self.player instanceof ServerPlayer serverPlayer) {
            // prevent chat spam
            long now = serverPlayer.level().getGameTime();

            if (now - torrential$lastMigrationMessageTime > 60L) {
                torrential$lastMigrationMessageTime = now;
                ModComponents.sendMigrationMessage(serverPlayer);
            }
        }
    }
}
