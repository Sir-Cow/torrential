package sircow.torrential.event;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sircow.torrential.Constants;
import sircow.torrential.potion.ModPotions;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class ForgeBrewingRecipeEventHandler {
    @SubscribeEvent
    public static void registerBrewingRecipes(BrewingRecipeRegisterEvent event) {
        var builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.RABBIT_HIDE, ModPotions.luckHolder());
        builder.addMix(ModPotions.luckHolder(), Items.REDSTONE, ModPotions.longLuckHolder());
        builder.addMix(ModPotions.luckHolder(), Items.GLOWSTONE_DUST, ModPotions.strongLuckHolder());
        builder.addMix(Potions.AWKWARD, Items.NAUTILUS_SHELL, ModPotions.nautilusBlessingHolder());
    }
}
