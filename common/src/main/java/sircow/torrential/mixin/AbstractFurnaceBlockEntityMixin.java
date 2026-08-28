package sircow.torrential.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sircow.torrential.block.ModBlocks;

import java.util.Map;

@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Inject(method = "getFuel", at = @At("RETURN"))
    private static void torrential$modifyFuelValues(CallbackInfoReturnable<Map<Item, Integer>> cir) {
        cir.getReturnValue().put(ModBlocks.ANGLING_TABLE.get().asItem(), 300);
    }
}
