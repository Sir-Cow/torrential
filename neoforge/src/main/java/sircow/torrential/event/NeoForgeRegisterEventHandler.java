package sircow.torrential.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import sircow.torrential.Constants;
import sircow.torrential.block.ModBlocks;
import sircow.torrential.component.ModComponents;
import sircow.torrential.item.ModItems;
import sircow.torrential.potion.ModPotions;
import sircow.torrential.sound.ModSounds;
import sircow.torrential.trigger.ModTriggers;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = Constants.MOD_ID)
public class NeoForgeRegisterEventHandler {
    private static final Map<ResourceLocation, Block> REGISTERED_BLOCKS = new HashMap<>();

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(Registries.BLOCK, helper ->
                ModBlocks.getBlocks().forEach((id, definition) -> {
                    Block block = definition.factory().get();
                    REGISTERED_BLOCKS.put(id, block);
                    helper.register(id, block);
                })
        );
        event.register(Registries.ITEM, helper -> {
            ModBlocks.getBlocks().forEach((id, definition) -> {
                Block block = REGISTERED_BLOCKS.get(id);
                if (block != null) {
                    helper.register(id, new BlockItem(block, new Item.Properties()));
                }
            });

            ModItems.getItems().forEach((id, supplier) -> helper.register(id, supplier.get()));
        });
        event.register(Registries.DATA_COMPONENT_TYPE, helper ->
                ModComponents.getComponents().forEach(helper::register)
        );
        event.register(Registries.SOUND_EVENT, helper ->
                ModSounds.getSounds().forEach(helper::register)
        );
        event.register(Registries.POTION, helper ->
                ModPotions.getPotions().forEach(helper::register)
        );
        event.register(Registries.TRIGGER_TYPE, helper ->
                ModTriggers.getTriggers().forEach(helper::register)
        );
    }

    @SubscribeEvent
    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        var builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.RABBIT_HIDE, ModPotions.luckHolder());
        builder.addMix(ModPotions.luckHolder(), Items.REDSTONE, ModPotions.longLuckHolder());
        builder.addMix(ModPotions.luckHolder(), Items.GLOWSTONE_DUST, ModPotions.strongLuckHolder());
    }
}
