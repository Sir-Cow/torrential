package sircow.torrential.trade;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class ModTrade implements VillagerTrades.ItemListing {
    private final ItemCost costA, costB;
    private final ItemStack result;
    private final int maxUses, villagerXp;
    private final float priceMultiplier;
    private final Set<VillagerType> requiredTypes;

    public ModTrade(ItemLike costA, int costACount, ItemLike result, int resultCount, int maxUses, int villagerXp, float priceMultiplier) {
        this.costA = new ItemCost(costA, costACount);
        this.costB = null;
        this.result = new ItemStack(result, resultCount);
        this.maxUses = maxUses;
        this.villagerXp = villagerXp;
        this.priceMultiplier = priceMultiplier;
        this.requiredTypes = Set.of();
    }

    public ModTrade(ItemLike costA, int costACount, ItemLike costB, int costBCount, ItemLike result, int resultCount, int maxUses, int villagerXp, float priceMultiplier) {
        this.costA = new ItemCost(costA, costACount);
        this.costB = new ItemCost(costB, costBCount);
        this.result = new ItemStack(result, resultCount);
        this.maxUses = maxUses;
        this.villagerXp = villagerXp;
        this.priceMultiplier = priceMultiplier;
        this.requiredTypes = Set.of();
    }

    public ModTrade(ItemLike costA, int costACount, ItemLike result, int resultCount, int maxUses, int villagerXp, float priceMultiplier, VillagerType... requiredTypes) {
        this.costA = new ItemCost(costA, costACount);
        this.costB = null;
        this.result = new ItemStack(result, resultCount);
        this.maxUses = maxUses;
        this.villagerXp = villagerXp;
        this.priceMultiplier = priceMultiplier;
        this.requiredTypes = new HashSet<>(Arrays.asList(requiredTypes));
    }

    @Override
    public MerchantOffer getOffer(@NotNull Entity trader, @NotNull RandomSource random) {
        if (!this.requiredTypes.isEmpty()) {
            if (!(trader instanceof Villager villager)) return null;
            if (!this.requiredTypes.contains(villager.getVillagerData().getType())) return null;
        }
        return new MerchantOffer(this.costA, Optional.ofNullable(this.costB), this.result, this.maxUses, this.villagerXp, this.priceMultiplier);
    }
}
