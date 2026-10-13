package sircow.torrential.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sircow.torrential.component.ModComponents;

import java.util.List;

@Mixin(Player.class)
public abstract class PlayerVanishingMixin {
    @Shadow public abstract Inventory getInventory();

    @Inject(method = "destroyVanishingCursedItems", at = @At("HEAD"))
    private void torrential$stripVanishingPartsFromRods(CallbackInfo ci) {
        Inventory inventory = this.getInventory();

        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !stack.is(Items.FISHING_ROD)) continue;

            boolean changed = false;

            if (stack.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()).contains(ModComponents.VANISHING_ID)) {
                stack.set(ModComponents.HOOK_COMPONENT, "none");
                stack.set(ModComponents.HOOK_DURABILITY, 0);
                stack.set(ModComponents.HOOK_ENCHANTMENTS, List.of());
                changed = true;
            }
            if (stack.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()).contains(ModComponents.VANISHING_ID)) {
                stack.set(ModComponents.LINE_COMPONENT, "none");
                stack.set(ModComponents.LINE_DURABILITY, 0);
                stack.set(ModComponents.LINE_ENCHANTMENTS, List.of());
                changed = true;
            }
            if (stack.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()).contains(ModComponents.VANISHING_ID)) {
                stack.set(ModComponents.SINKER_COMPONENT, "none");
                stack.set(ModComponents.SINKER_DURABILITY, 0);
                stack.set(ModComponents.SINKER_ENCHANTMENTS, List.of());
                changed = true;
            }

            if (changed) inventory.setChanged();
        }
    }
}
