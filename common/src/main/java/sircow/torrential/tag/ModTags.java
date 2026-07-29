package sircow.torrential.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import sircow.torrential.Constants;

public class ModTags {
    public static final TagKey<Item> HOOKS = TagKey.create(Registries.ITEM, Constants.id("hooks"));
    public static final TagKey<Item> LINES = TagKey.create(Registries.ITEM, Constants.id("lines"));
    public static final TagKey<Item> SINKERS = TagKey.create(Registries.ITEM, Constants.id("sinkers"));
    public static final TagKey<Item> ROD_UPGRADES = TagKey.create(Registries.ITEM, Constants.id("rod_upgrades"));
    public static final TagKey<Item> FISHING_LOOT_FISH = TagKey.create(Registries.ITEM, Constants.id("fishing_loot/fish"));
    public static final TagKey<Item> FISHING_LOOT_JUNK = TagKey.create(Registries.ITEM, Constants.id("fishing_loot/junk"));
    public static final TagKey<Item> FISHING_LOOT_TREASURE = TagKey.create(Registries.ITEM, Constants.id("fishing_loot/treasure"));
    public static final TagKey<Item> FISHING_LOOT_VARIETY = TagKey.create(Registries.ITEM, Constants.id("fishing_loot/variety"));

    public static void registerModTags() {}
}
