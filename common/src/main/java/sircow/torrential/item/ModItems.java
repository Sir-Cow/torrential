package sircow.torrential.item;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemContainerContents;
import sircow.torrential.item.custom.CacheItem;

import java.util.function.Function;

public class ModItems {
    public static final Item AQUATIC_FIBER = registerItem(ModItemIds.AQUATIC_FIBER, new Item.Properties());
    public static final Item CACHE = registerItem(ModItemIds.CACHE,
            properties ->
            new CacheItem(properties, 18),
            new Item.Properties()
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)
    );
    public static final Item COPPER_FISHING_HOOK = registerItem(
            ModItemIds.COPPER_FISHING_HOOK,
            new Item.Properties()
                    .durability(190)
                    .stacksTo(1)
                    .repairable(ItemTags.COPPER_TOOL_MATERIALS)
    );
    public static final Item IRON_FISHING_HOOK = registerItem(
            ModItemIds.IRON_FISHING_HOOK,
            new Item.Properties()
                    .durability(250)
                    .stacksTo(1)
                    .repairable(ItemTags.IRON_TOOL_MATERIALS)
    );
    public static final Item PRISMARINE_FISHING_HOOK = registerItem(
            ModItemIds.PRISMARINE_FISHING_HOOK,
            new Item.Properties()
                    .durability(768)
                    .stacksTo(1)
    );
    public static final Item DIAMOND_FISHING_HOOK = registerItem(
            ModItemIds.DIAMOND_FISHING_HOOK,
            new Item.Properties()
                    .durability(1562)
                    .stacksTo(1)
                    .repairable(ItemTags.DIAMOND_TOOL_MATERIALS)
    );
    public static final Item NETHERITE_FISHING_HOOK = registerItem(
            ModItemIds.NETHERITE_FISHING_HOOK,
            new Item.Properties()
                    .durability(2032)
                    .stacksTo(1)
                    .repairable(ItemTags.NETHERITE_TOOL_MATERIALS)
                    .fireResistant()
    );
    public static final Item COPPER_LACED_FISHING_LINE = registerItem(
            ModItemIds.COPPER_LACED_FISHING_LINE,
            new Item.Properties()
                    .durability(190)
                    .stacksTo(1)
                    .repairable(ItemTags.COPPER_TOOL_MATERIALS)
    );
    public static final Item IRON_LACED_FISHING_LINE = registerItem(
            ModItemIds.IRON_LACED_FISHING_LINE,
            new Item.Properties()
                    .durability(250)
                    .stacksTo(1)
                    .repairable(ItemTags.IRON_TOOL_MATERIALS)
    );
    public static final Item PRISMARINE_LACED_FISHING_LINE = registerItem(
            ModItemIds.PRISMARINE_LACED_FISHING_LINE,
            new Item.Properties()
                    .durability(768)
                    .stacksTo(1)
    );
    public static final Item DIAMOND_LACED_FISHING_LINE = registerItem(
            ModItemIds.DIAMOND_LACED_FISHING_LINE,
            new Item.Properties()
                    .durability(1562)
                    .stacksTo(1)
                    .repairable(ItemTags.DIAMOND_TOOL_MATERIALS)
    );
    public static final Item NETHERITE_LACED_FISHING_LINE = registerItem(
            ModItemIds.NETHERITE_LACED_FISHING_LINE,
            new Item.Properties()
                    .durability(2032)
                    .stacksTo(1)
                    .repairable(ItemTags.NETHERITE_TOOL_MATERIALS)
                    .fireResistant()
    );
    public static final Item COPPER_SINKER = registerItem(
            ModItemIds.COPPER_SINKER,
            new Item.Properties()
                    .durability(190)
                    .stacksTo(1)
                    .repairable(ItemTags.COPPER_TOOL_MATERIALS)
    );
    public static final Item IRON_SINKER = registerItem(
            ModItemIds.IRON_SINKER,
            new Item.Properties()
                    .durability(250)
                    .stacksTo(1)
                    .repairable(ItemTags.IRON_TOOL_MATERIALS)
    );
    public static final Item PRISMARINE_SINKER = registerItem(
            ModItemIds.PRISMARINE_SINKER,
            new Item.Properties()
                    .durability(768)
                    .stacksTo(1)
    );
    public static final Item DIAMOND_SINKER = registerItem(
            ModItemIds.DIAMOND_SINKER,
            new Item.Properties()
                    .durability(1562)
                    .stacksTo(1)
                    .repairable(ItemTags.DIAMOND_TOOL_MATERIALS)
    );
    public static final Item NETHERITE_SINKER = registerItem(
            ModItemIds.NETHERITE_SINKER,
            new Item.Properties()
                    .durability(2032)
                    .stacksTo(1)
                    .repairable(ItemTags.NETHERITE_TOOL_MATERIALS)
                    .fireResistant()
    );


    private static Item registerItem(ResourceKey<Item> id, Item.Properties properties) {
        return registerItem(id, Item::new, properties);
    }

    private static Item registerItem(ResourceKey<Item> id, Function<Item.Properties, Item> itemFactory, Item.Properties properties) {
        Item item = itemFactory.apply(properties.setId(id));
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, item);
        }

        return Registry.register(BuiltInRegistries.ITEM, id, item);
    }

    public static void registerModItems() {}
}
