package sircow.torrential.event;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import sircow.torrential.Constants;
import sircow.torrential.trade.ModTrades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EventBusSubscriber(modid = Constants.MOD_ID)
public final class NeoForgeVillagerTradesEvent {
    private NeoForgeVillagerTradesEvent() {}

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerProfession.FISHERMAN) return;

        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        trades.clear();
        Int2ObjectMap<VillagerTrades.ItemListing[]> modTrades = ModTrades.create();

        for (Int2ObjectMap.Entry<VillagerTrades.ItemListing[]> entry : modTrades.int2ObjectEntrySet()) {
            trades.put(entry.getIntKey(), new ArrayList<>(Arrays.asList(entry.getValue())));
        }
    }
}
