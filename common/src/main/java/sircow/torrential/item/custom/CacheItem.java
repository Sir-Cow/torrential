package sircow.torrential.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemContainerContents;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CacheItem extends Item {
    private final int size;
    private static final Component UNKNOWN_CONTENTS = Component.translatable("container.shulkerBox.unknownContents");

    public CacheItem(Properties properties, int size) {
        super(properties);
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    public void appendHoverText(@NotNull ItemStack itemStack, Item.@NotNull TooltipContext tooltipContext, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        if (itemStack.has(DataComponents.CONTAINER_LOOT)) list.add(UNKNOWN_CONTENTS);

        int i = 0;
        int j = 0;

        for(ItemStack itemStack2 : itemStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).nonEmptyItems()) {
            ++j;
            if (i <= 4) {
                ++i;
                list.add(Component.translatable("container.shulkerBox.itemCount", itemStack2.getHoverName(), itemStack2.getCount()));
            }
        }

        if (j - i > 0) list.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
    }
}
