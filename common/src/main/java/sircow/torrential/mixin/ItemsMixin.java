package sircow.torrential.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import sircow.torrential.component.ModComponents;

@Mixin(Items.class)
public class ItemsMixin {
    @ModifyArg(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=fishing_rod")), at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Items;registerItem(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/item/Item$Properties;)Lnet/minecraft/world/item/Item;", ordinal = 0))
    private static Item.Properties torrential$modifyFishingRod(Item.Properties properties) {
        return new Item.Properties()
                .stacksTo(1)
                .component(ModComponents.HOOK_COMPONENT, "none")
                .component(ModComponents.HOOK_DURABILITY, 0)
                .component(ModComponents.LINE_COMPONENT, "none")
                .component(ModComponents.LINE_DURABILITY, 0)
                .component(ModComponents.SINKER_COMPONENT, "none")
                .component(ModComponents.SINKER_DURABILITY, 0)
                .component(ModComponents.HOOK_UNBREAKING, 0)
                .component(ModComponents.LINE_UNBREAKING, 0)
                .component(ModComponents.SINKER_UNBREAKING, 0);
    }
}
