package sircow.torrential.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import sircow.torrential.block.custom.AnglingTableBlock;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    private static final Map<BlockItemId, Definition> BLOCKS = new LinkedHashMap<>();

    public static Supplier<Block> ANGLING_TABLE = register(
            ModBlockItemIds.ANGLING_TABLE,
            AnglingTableBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(2.5F)
                    .sound(SoundType.WOOD)
                    .ignitedByLava()
    );

    private static Supplier<Block> register(BlockItemId id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
        Supplier<Block> memoizedSupplier = new Supplier<>() {
            private Block instance;

            @Override
            public Block get() {
                if (instance == null) instance = factory.apply(properties.setId(id.block()));
                return instance;
            }
        };

        BLOCKS.put(id, new Definition(memoizedSupplier));
        return memoizedSupplier;
    }

    public static Map<BlockItemId, Definition> getBlocks() {
        return BLOCKS;
    }

    public static Item.Properties createBlockItemProperties(BlockItemId id) {
        Item.Properties properties = new Item.Properties().setId(id.item());
        if (ModBlockItemIds.ANGLING_TABLE.equals(id)) {
            properties.cookingFuel(ContextIntProviders.COOKING_TIME_WOOD_BLOCKS);
        }
        return properties;
    }

    public record Definition(Supplier<Block> factory) {}

    public static void registerModBlocks() {}
}
