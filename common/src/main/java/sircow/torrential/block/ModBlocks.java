package sircow.torrential.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import sircow.torrential.block.custom.AnglingTableBlock;

import java.util.function.Function;

public class ModBlocks {
    public static final Block ANGLING_TABLE = register(
            ModBlockItemIds.ANGLING_TABLE,
            AnglingTableBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
    );

    private static Block register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        Block block = factory.apply(properties.setId(id.block()));
        BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(id.item()));
        Registry.register(BuiltInRegistries.ITEM, id.item(), blockItem);

        return Registry.register(BuiltInRegistries.BLOCK, id.block(), block);
    }

    public static void registerModBlocks() {}
}
