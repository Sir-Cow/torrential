package sircow.torrential.mixin;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sircow.torrential.component.RodComponentResolver;
import sircow.torrential.component.RodTooltipComponent;

import java.util.Optional;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "getTooltipImage", at = @At("HEAD"), cancellable = true)
    private void torrential$fishingRodTooltip(ItemStack itemStack, CallbackInfoReturnable<Optional<TooltipComponent>> cir) {
        if (!(itemStack.getItem() instanceof FishingRodItem)) return;

        ItemStack hook = RodComponentResolver.resolveHook(itemStack);
        ItemStack line = RodComponentResolver.resolveLine(itemStack);
        ItemStack sinker = RodComponentResolver.resolveSinker(itemStack);

        cir.setReturnValue(Optional.of(new RodTooltipComponent(itemStack, hook, line, sinker)));
    }
}
