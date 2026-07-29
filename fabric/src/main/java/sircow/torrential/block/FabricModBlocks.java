package sircow.torrential.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class FabricModBlocks {
    public static void registerFabricModBlocks() {
        ModBlocks.getBlocks().forEach((id, definition) -> {
            Block block = definition.factory().get();
            Registry.register(BuiltInRegistries.BLOCK, id.block(), block);
            Registry.register(BuiltInRegistries.ITEM, id.item(), new BlockItem(block, new Item.Properties().setId(id.item())));
        });
    }
}
