package sircow.torrential.trade;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Items;
import sircow.torrential.item.ModItems;

public class ModTrades {
    private ModTrades() {}

    public static Int2ObjectMap<VillagerTrades.ItemListing[]> create() {
        return new Int2ObjectOpenHashMap<>(ImmutableMap.of(
                1, new VillagerTrades.ItemListing[]{
                        new ModTrade(Items.COAL, 10, Items.EMERALD, 1, 16, 2, 0.05f),
                        new ModTrade(Items.EMERALD, 3, Items.COD_BUCKET, 1, 16, 1, 0.05F),
                        new ModTrade(Items.COD, 6, Items.EMERALD, 1, Items.COOKED_COD, 6, 16, 1, 0.05F),
                        new ModTrade(Items.STRING, 20, Items.EMERALD, 1, 16, 2, 0.05F)
                },
                2, new VillagerTrades.ItemListing[]{
                        new ModTrade(Items.COD, 15, Items.EMERALD, 1, 16, 10, 0.05F),
                        new ModTrade(Items.EMERALD, 2, Items.CAMPFIRE, 1, 12, 5, 0.05F),
                        new ModTrade(Items.SALMON, 6, Items.EMERALD, 1, Items.COOKED_SALMON, 6, 16, 5, 0.05F)
                },
                3, new VillagerTrades.ItemListing[]{
                        new ModTrade(Items.PUFFERFISH, 4, Items.EMERALD, 1, 12, 10, 0.05F),
                        new ModTrade(Items.SALMON, 13, Items.EMERALD, 1, 16, 20, 0.05F)
                },
                4, new VillagerTrades.ItemListing[]{
                        new ModTrade(Items.EMERALD, 12, ModItems.IRON_FISHING_HOOK.get(), 1, 3, 30, 0.2F),
                        new ModTrade(Items.EMERALD, 12, ModItems.IRON_LACED_FISHING_LINE.get(), 1, 3, 30, 0.2F),
                        new ModTrade(Items.EMERALD, 12, ModItems.IRON_SINKER.get(), 1, 3, 30, 0.2F),
                        new ModTrade(Items.TROPICAL_FISH, 6, Items.EMERALD, 1, 12, 30, 0.05F)
                },
                5, new VillagerTrades.ItemListing[]{
                        new ModTrade(Items.ACACIA_BOAT, 1, Items.EMERALD, 1, 12, 30, 0.05F, VillagerType.SAVANNA),
                        new ModTrade(Items.DARK_OAK_BOAT, 1, Items.EMERALD, 1, 12, 30, 0.05F, VillagerType.SWAMP),
                        new ModTrade(Items.EMERALD, 32, ModItems.DIAMOND_FISHING_HOOK.get(), 1, 3, 30, 0.2F),
                        new ModTrade(Items.EMERALD, 38, ModItems.DIAMOND_LACED_FISHING_LINE.get(), 1, 3, 30, 0.2F),
                        new ModTrade(Items.EMERALD, 26, ModItems.DIAMOND_SINKER.get(), 1, 3, 30, 0.2F),
                        new ModTrade(Items.JUNGLE_BOAT, 1, Items.EMERALD, 1, 12, 30, 0.05F, VillagerType.DESERT, VillagerType.JUNGLE),
                        new ModTrade(Items.OAK_BOAT, 1, Items.EMERALD, 1, 12, 30, 0.05F, VillagerType.PLAINS),
                        new ModTrade(Items.SPRUCE_BOAT, 1, Items.EMERALD, 1, 12, 30, 0.05F, VillagerType.SNOW, VillagerType.TAIGA)
                }
        ));
    }
}
