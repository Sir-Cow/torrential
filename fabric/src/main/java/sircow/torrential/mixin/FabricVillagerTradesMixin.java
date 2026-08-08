package sircow.torrential.mixin;

import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sircow.torrential.trade.ModTrades;

@Mixin(VillagerTrades.class)
public class FabricVillagerTradesMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void torrential$replaceFishermanTrades(CallbackInfo ci) {
        VillagerTrades.TRADES.put(VillagerProfession.FISHERMAN, ModTrades.create());
    }
}
