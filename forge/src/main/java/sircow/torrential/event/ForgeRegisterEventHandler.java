package sircow.torrential.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import sircow.torrential.Constants;
import sircow.torrential.block.ModBlocks;
import sircow.torrential.component.ModComponents;
import sircow.torrential.item.ModItems;
import sircow.torrential.potion.ModPotions;
import sircow.torrential.sound.ModSounds;
import sircow.torrential.trigger.ModTriggers;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeRegisterEventHandler {
    private static final Map<Identifier, Block> REGISTERED_BLOCKS = new HashMap<>();

    @SubscribeEvent
    public static void register(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.BLOCKS, helper ->
                ModBlocks.getBlocks().forEach((id, definition) -> {
                    Block block = definition.factory().get();
                    REGISTERED_BLOCKS.put(id, block);
                    helper.register(id, block);
                })
        );
        event.register(ForgeRegistries.Keys.ITEMS, helper -> {
            ModBlocks.getBlocks().forEach((id, definition) -> {
                Block block = REGISTERED_BLOCKS.get(id);
                if (block != null) {
                    helper.register(id, new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
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
}
