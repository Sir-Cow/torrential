package sircow.torrential.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sircow.torrential.Constants;
import sircow.torrential.codec.ItemData;
import sircow.torrential.item.custom.CacheItem;
import sircow.torrential.sound.ModSounds;
import sircow.torrential.tag.ModTags;

public class CacheMenu extends AbstractContainerMenu {
    private final Container container;
    private final ItemStack stackContext;

    public CacheMenu(int syncId, Inventory playerInventory, Container container, ItemStack stackContext) {
        super(Constants.CACHE_MENU_TYPE.get(), syncId);
        this.container = container;
        this.stackContext = stackContext;
        container.startOpen(playerInventory.player);
        checkContainerSize(container, 18);
        setupSlots(playerInventory);
    }

    public CacheMenu(int containerId, Inventory playerInventory, ItemData data) {
        super(Constants.CACHE_MENU_TYPE.get(), containerId);
        this.container = new SimpleContainer(data.containerSize());
        this.stackContext = ItemStack.EMPTY;
        checkContainerSize(this.container, data.containerSize());
        setupSlots(playerInventory);
    }

    private void setupSlots(Inventory playerInventory) {
        int startX = 8;
        int startY = 17;
        int slotSize = 18;

        for (int row = 0; row < 2; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new CacheSlot(this.container, col + row * 9, startX + col * slotSize, startY + row * slotSize));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 66 + row * 18));
            }
        }

        for (int col = 0; col < 9; ++col) {
            this.addSlot(new Slot(playerInventory, col, 8 + col * 18, 124));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (itemstack1.getItem() instanceof CacheItem) return ItemStack.EMPTY;

            if (index < 18) {
                if (!this.moveItemStackTo(itemstack1, 18, this.slots.size(), true)) return ItemStack.EMPTY;
            }
            else if (index < 45) {
                if (!this.moveItemStackTo(itemstack1, 0, 18, false) && !this.moveItemStackTo(itemstack1, 45, this.slots.size(), false)) return ItemStack.EMPTY;
            }
            else if (index < this.slots.size()) {
                if (!this.moveItemStackTo(itemstack1, 0, 18, false) && !this.moveItemStackTo(itemstack1, 18, 45, false)) return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return this.stackContext != null && !this.stackContext.isEmpty() && player.getInventory().contains(this.stackContext) && this.container.stillValid(player);
    }

    private static class CacheSlot extends Slot {
        public CacheSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return !(stack.getItem() instanceof CacheItem || stack.is(ModTags.SHULKER_BOXES));
        }
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.container.stopOpen(player);
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), ModSounds.CACHE_CLOSE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
