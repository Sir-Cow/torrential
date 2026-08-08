package sircow.torrential.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;
import sircow.torrential.item.custom.CacheItem;
import sircow.torrential.tag.ModTags;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModItems {
    private static final Map<ResourceKey<Item>, Supplier<Item>> ITEMS = new LinkedHashMap<>();

    public static final Supplier<Item> AQUATIC_FIBER = registerItem(ModItemIds.AQUATIC_FIBER, Item.Properties::new);
    public static final Supplier<Item> CACHE = registerItem(
            ModItemIds.CACHE,
            properties -> new CacheItem(properties, 18),
            () -> new Item.Properties()
                    .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY)
                    .rarity(Rarity.UNCOMMON)
                    .stacksTo(1)
    );
    public static final Supplier<Item> COPPER_FISHING_HOOK = registerItem(
            ModItemIds.COPPER_FISHING_HOOK,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.COPPER_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(190)
                    .stacksTo(1)
    );
    public static final Supplier<Item> IRON_FISHING_HOOK = registerItem(
            ModItemIds.IRON_FISHING_HOOK,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.IRON_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(250)
                    .stacksTo(1)
    );
    public static final Supplier<Item> PRISMARINE_FISHING_HOOK = registerItem(
            ModItemIds.PRISMARINE_FISHING_HOOK,
            () -> new Item.Properties()
                    .durability(768)
                    .stacksTo(1)
    );
    public static final Supplier<Item> DIAMOND_FISHING_HOOK = registerItem(
            ModItemIds.DIAMOND_FISHING_HOOK,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.DIAMONDS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(1562)
                    .stacksTo(1)
    );
    public static final Supplier<Item> NETHERITE_FISHING_HOOK = registerItem(
            ModItemIds.NETHERITE_FISHING_HOOK,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.NETHERITE_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(2032)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Supplier<Item> COPPER_LACED_FISHING_LINE = registerItem(
            ModItemIds.COPPER_LACED_FISHING_LINE,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.COPPER_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(190)
                    .stacksTo(1)
    );
    public static final Supplier<Item> IRON_LACED_FISHING_LINE = registerItem(
            ModItemIds.IRON_LACED_FISHING_LINE,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.IRON_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(250)
                    .stacksTo(1)
    );
    public static final Supplier<Item> PRISMARINE_LACED_FISHING_LINE = registerItem(
            ModItemIds.PRISMARINE_LACED_FISHING_LINE,
            () -> new Item.Properties()
                    .durability(768)
                    .stacksTo(1)
    );
    public static final Supplier<Item> DIAMOND_LACED_FISHING_LINE = registerItem(
            ModItemIds.DIAMOND_LACED_FISHING_LINE,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.DIAMONDS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(1562)
                    .stacksTo(1)
    );
    public static final Supplier<Item> NETHERITE_LACED_FISHING_LINE = registerItem(
            ModItemIds.NETHERITE_LACED_FISHING_LINE,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.NETHERITE_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(2032)
                    .stacksTo(1)
                    .fireResistant()
    );
    public static final Supplier<Item> COPPER_SINKER = registerItem(
            ModItemIds.COPPER_SINKER,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.COPPER_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(190)
                    .stacksTo(1)
    );
    public static final Supplier<Item> IRON_SINKER = registerItem(
            ModItemIds.IRON_SINKER,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.IRON_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(250)
                    .stacksTo(1)
    );
    public static final Supplier<Item> PRISMARINE_SINKER = registerItem(
            ModItemIds.PRISMARINE_SINKER,
            () -> new Item.Properties()
                    .durability(768)
                    .stacksTo(1)
    );
    public static final Supplier<Item> DIAMOND_SINKER = registerItem(
            ModItemIds.DIAMOND_SINKER,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.DIAMONDS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(1562)
                    .stacksTo(1)
    );
    public static final Supplier<Item> NETHERITE_SINKER = registerItem(
            ModItemIds.NETHERITE_SINKER,
            properties -> new Item(properties) {
                @Override
                public boolean isValidRepairItem(@NotNull ItemStack stack, @NotNull ItemStack repairCandidate) {
                    return repairCandidate.is(ModTags.NETHERITE_INGOTS) || super.isValidRepairItem(stack, repairCandidate);
                }
            },
            () -> new Item.Properties()
                    .durability(2032)
                    .stacksTo(1)
                    .fireResistant()
    );

    private static Supplier<Item> registerItem(ResourceKey<Item> id, Supplier<Item.Properties> propertiesSupplier) {
        return registerItem(id, Item::new, propertiesSupplier);
    }

    private static Supplier<Item> registerItem(ResourceKey<Item> id, Function<Item.Properties, Item> factory, Supplier<Item.Properties> propertiesSupplier) {
        Supplier<Item> memoizedSupplier = new Supplier<>() {
            private Item instance;

            @Override
            public Item get() {
                if (instance == null) instance = factory.apply(propertiesSupplier.get());
                return instance;
            }
        };

        ITEMS.put(id, memoizedSupplier);
        return memoizedSupplier;
    }

    public static Map<ResourceKey<Item>, Supplier<Item>> getItems() {
        return ITEMS;
    }

    public static void registerModItems() {}
}
