package sircow.torrential.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import sircow.torrential.Constants;

public class ModItemIds {
    public static final ResourceKey<Item> AQUATIC_FIBER = create("aquatic_fiber");
    public static final ResourceKey<Item> CACHE = create("cache");
    public static final ResourceKey<Item> COPPER_FISHING_HOOK = create("copper_fishing_hook");
    public static final ResourceKey<Item> IRON_FISHING_HOOK = create("iron_fishing_hook");
    public static final ResourceKey<Item> PRISMARINE_FISHING_HOOK = create("prismarine_fishing_hook");
    public static final ResourceKey<Item> DIAMOND_FISHING_HOOK = create("diamond_fishing_hook");
    public static final ResourceKey<Item> NETHERITE_FISHING_HOOK = create("netherite_fishing_hook");
    public static final ResourceKey<Item> COPPER_LACED_FISHING_LINE = create("copper_laced_fishing_line");
    public static final ResourceKey<Item> IRON_LACED_FISHING_LINE = create("iron_laced_fishing_line");
    public static final ResourceKey<Item> PRISMARINE_LACED_FISHING_LINE = create("prismarine_laced_fishing_line");
    public static final ResourceKey<Item> DIAMOND_LACED_FISHING_LINE = create("diamond_laced_fishing_line");
    public static final ResourceKey<Item> NETHERITE_LACED_FISHING_LINE = create("netherite_laced_fishing_line");
    public static final ResourceKey<Item> COPPER_SINKER = create("copper_sinker");
    public static final ResourceKey<Item> IRON_SINKER = create("iron_sinker");
    public static final ResourceKey<Item> PRISMARINE_SINKER = create("prismarine_sinker");
    public static final ResourceKey<Item> DIAMOND_SINKER = create("diamond_sinker");
    public static final ResourceKey<Item> NETHERITE_SINKER = create("netherite_sinker");

    private static ResourceKey<Item> create(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }
}
