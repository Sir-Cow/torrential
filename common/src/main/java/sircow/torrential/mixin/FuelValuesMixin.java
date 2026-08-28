package sircow.torrential.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import it.unimi.dsi.fastutil.objects.Object2IntSortedMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.FuelValues;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sircow.torrential.block.ModBlocks;

@Mixin(FuelValues.class)
public class FuelValuesMixin {
    @ModifyReturnValue(method = "vanillaBurnTimes(Lnet/minecraft/core/HolderLookup$Provider;Lnet/minecraft/world/flag/FeatureFlagSet;I)Lnet/minecraft/world/level/block/entity/FuelValues;", at = @At("RETURN"))
    private static FuelValues torrential$modifyFuelValues(FuelValues original, HolderLookup.Provider registries, FeatureFlagSet enabledFeatures, int baseUnit) {
        FuelValuesAccessor accessor = (FuelValuesAccessor) original;
        Object2IntSortedMap<Item> values = accessor.getValues();

        values.put(ModBlocks.ANGLING_TABLE.get().asItem(), (int) (baseUnit * 1.5F));

        return original;
    }
}
