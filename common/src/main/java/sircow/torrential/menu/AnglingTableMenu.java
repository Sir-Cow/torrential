package sircow.torrential.menu;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import sircow.torrential.Constants;
import sircow.torrential.block.ModBlocks;
import sircow.torrential.component.ModComponents;
import sircow.torrential.item.ModItems;
import sircow.torrential.sound.ModSounds;
import sircow.torrential.tag.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnglingTableMenu extends AbstractContainerMenu {
    private final Slot rodInputSlot, hookInputSlot, lineInputSlot, sinkerInputSlot;
    private boolean hookPresent, linePresent, sinkerPresent;
    private final Player player;

    Runnable slotUpdateListener = () -> {};

    private final Container inputContainer = new SimpleContainer(4) {
        @Override
        public void setChanged() {
            super.setChanged();
            AnglingTableMenu.this.slotsChanged(this);
            AnglingTableMenu.this.slotUpdateListener.run();
        }
    };
    private final ContainerLevelAccess access;

    public AnglingTableMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public AnglingTableMenu(int containerId, Inventory playerInventory, final ContainerLevelAccess access) {
        super(Constants.ANGLING_TABLE_MENU_TYPE.get(), containerId);
        this.access = access;
        this.player = playerInventory.player;
        rodInputSlot = this.addSlot(new Slot(this.inputContainer, 0, 79, 17) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return stack.is(Items.FISHING_ROD);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                if (!Objects.equals(stack.get(ModComponents.HOOK_COMPONENT), "none")) {
                    AnglingTableMenu.this.hookInputSlot.set(ItemStack.EMPTY);
                    AnglingTableMenu.this.hookPresent = false;
                }
                if (!Objects.equals(stack.get(ModComponents.LINE_COMPONENT), "none")) {
                    AnglingTableMenu.this.lineInputSlot.set(ItemStack.EMPTY);
                    AnglingTableMenu.this.linePresent = false;
                }
                if (!Objects.equals(stack.get(ModComponents.SINKER_COMPONENT), "none")) {
                    AnglingTableMenu.this.sinkerInputSlot.set(ItemStack.EMPTY);
                    AnglingTableMenu.this.sinkerPresent = false;
                }

                AnglingTableMenu.this.access.execute((level, blockPos) -> level.playSound(null, blockPos, ModSounds.ANGLING_TABLE_USE, SoundSource.BLOCKS, 1.0F, 1.0F));

                super.onTake(player, stack);
            }
        });
        hookInputSlot = this.addSlot(new Slot(this.inputContainer, 1, 56, 51) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                if (!stack.is(ModTags.HOOKS) || AnglingTableMenu.this.rodInputSlot.getItem().isEmpty()) return false;
                return !hasEnchantment(AnglingTableMenu.this.rodInputSlot.getItem(), ModComponents.HOOK_ENCHANTMENTS, ModComponents.BINDING_ID);
            }

            @Override
            public boolean mayPickup(@NotNull Player player) {
                return !hasEnchantment(AnglingTableMenu.this.rodInputSlot.getItem(), ModComponents.HOOK_ENCHANTMENTS, ModComponents.BINDING_ID);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                ItemStack rod = AnglingTableMenu.this.rodInputSlot.getItem();
                if (!rod.isEmpty()) {
                    rod.set(ModComponents.HOOK_COMPONENT, "none");
                    rod.set(ModComponents.HOOK_DURABILITY, 0);
                    rod.set(ModComponents.HOOK_ENCHANTMENTS, List.of());
                    AnglingTableMenu.this.rodInputSlot.setChanged();
                }
                AnglingTableMenu.this.hookInputSlot.set(ItemStack.EMPTY);
                hookPresent = false;
                super.onTake(player, stack);
            }
        });
        lineInputSlot = this.addSlot(new Slot(this.inputContainer, 2, 79, 58) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                if (!stack.is(ModTags.LINES) || AnglingTableMenu.this.rodInputSlot.getItem().isEmpty()) return false;
                return !hasEnchantment(AnglingTableMenu.this.rodInputSlot.getItem(), ModComponents.LINE_ENCHANTMENTS, ModComponents.BINDING_ID);
            }

            @Override
            public boolean mayPickup(@NotNull Player player) {
                return !hasEnchantment(AnglingTableMenu.this.rodInputSlot.getItem(), ModComponents.LINE_ENCHANTMENTS, ModComponents.BINDING_ID);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                ItemStack rod = AnglingTableMenu.this.rodInputSlot.getItem();
                if (!rod.isEmpty()) {
                    rod.set(ModComponents.LINE_COMPONENT, "none");
                    rod.set(ModComponents.LINE_DURABILITY, 0);
                    rod.set(ModComponents.LINE_ENCHANTMENTS, List.of());
                    AnglingTableMenu.this.rodInputSlot.setChanged();
                }
                AnglingTableMenu.this.lineInputSlot.set(ItemStack.EMPTY);
                linePresent = false;
                super.onTake(player, stack);
            }
        });
        sinkerInputSlot = this.addSlot(new Slot(this.inputContainer, 3, 102, 51) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                if (!stack.is(ModTags.SINKERS) || AnglingTableMenu.this.rodInputSlot.getItem().isEmpty()) return false;
                return !hasEnchantment(AnglingTableMenu.this.rodInputSlot.getItem(), ModComponents.SINKER_ENCHANTMENTS, ModComponents.BINDING_ID);
            }

            @Override
            public boolean mayPickup(@NotNull Player player) {
                return !hasEnchantment(AnglingTableMenu.this.rodInputSlot.getItem(), ModComponents.SINKER_ENCHANTMENTS, ModComponents.BINDING_ID);
            }

            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
                ItemStack rod = AnglingTableMenu.this.rodInputSlot.getItem();
                if (!rod.isEmpty()) {
                    rod.set(ModComponents.SINKER_COMPONENT, "none");
                    rod.set(ModComponents.SINKER_DURABILITY, 0);
                    rod.set(ModComponents.SINKER_ENCHANTMENTS, List.of());
                    AnglingTableMenu.this.rodInputSlot.setChanged();
                }
                AnglingTableMenu.this.sinkerInputSlot.set(ItemStack.EMPTY);
                sinkerPresent = false;
                super.onTake(player, stack);
            }
        });
        this.addStandardInventorySlots(playerInventory, 8, 84);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemStack1 = slot.getItem();
            itemStack = itemStack1.copy();
            if (index >= 0 && index < 4) {
                if (!slot.mayPickup(player)) {
                    return ItemStack.EMPTY;
                }
                if (!this.moveItemStackTo(itemStack1, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
            }

            else if (index >= 4 && index < 40) {
                if (itemStack1.is(Items.FISHING_ROD)) {
                    if (!this.moveItemStackTo(itemStack1, this.rodInputSlot.index, this.rodInputSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (itemStack1.is(ModTags.HOOKS)) {
                    if (!this.moveItemStackTo(itemStack1, this.hookInputSlot.index, this.hookInputSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (itemStack1.is(ModTags.LINES)) {
                    if (!this.moveItemStackTo(itemStack1, this.lineInputSlot.index, this.lineInputSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (itemStack1.is(ModTags.SINKERS)) {
                    if (!this.moveItemStackTo(itemStack1, this.sinkerInputSlot.index, this.sinkerInputSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (itemStack1.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
            else slot.setChanged();

            if (itemStack1.getCount() == itemStack.getCount()) return ItemStack.EMPTY;

            slot.onTake(player, itemStack1);
        }
        return itemStack;
    }

    @Override
    public void slotsChanged(@NotNull Container inventory) {
        ItemStack itemstack = this.rodInputSlot.getItem();

        if (!itemstack.isEmpty()) this.handleUpgrades(this.player);
        this.broadcastChanges();
    }

    public void handleUpgrades(Player player) {
        ItemStack rod = this.rodInputSlot.getItem();
        ItemStack hook = this.hookInputSlot.getItem();
        ItemStack line = this.lineInputSlot.getItem();
        ItemStack sinker = this.sinkerInputSlot.getItem();

        // hooks
        if (!hook.isEmpty()) {
            if (hook.getItem() == ModItems.COPPER_FISHING_HOOK.get() && !Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "copper")) {
                rod.set(ModComponents.HOOK_COMPONENT, "copper");
                rod.set(ModComponents.HOOK_DURABILITY, hook.getDamageValue());
                captureEnchantments(rod, ModComponents.HOOK_ENCHANTMENTS, hook, player.level());
            }
            if (hook.getItem() == ModItems.PRISMARINE_FISHING_HOOK.get() && !Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "prismarine")) {
                rod.set(ModComponents.HOOK_COMPONENT, "prismarine");
                rod.set(ModComponents.HOOK_DURABILITY, hook.getDamageValue());
                captureEnchantments(rod, ModComponents.HOOK_ENCHANTMENTS, hook, player.level());
            }
            if (hook.getItem() == ModItems.IRON_FISHING_HOOK.get() && !Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "iron")) {
                rod.set(ModComponents.HOOK_COMPONENT, "iron");
                rod.set(ModComponents.HOOK_DURABILITY, hook.getDamageValue());
                captureEnchantments(rod, ModComponents.HOOK_ENCHANTMENTS, hook, player.level());
            }
            if (hook.getItem() == ModItems.DIAMOND_FISHING_HOOK.get() && !Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "diamond")) {
                rod.set(ModComponents.HOOK_COMPONENT, "diamond");
                rod.set(ModComponents.HOOK_DURABILITY, hook.getDamageValue());
                captureEnchantments(rod, ModComponents.HOOK_ENCHANTMENTS, hook, player.level());
            }
            if (hook.getItem() == ModItems.NETHERITE_FISHING_HOOK.get() && !Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "netherite")) {
                rod.set(ModComponents.HOOK_COMPONENT, "netherite");
                rod.set(ModComponents.HOOK_DURABILITY, hook.getDamageValue());
                captureEnchantments(rod, ModComponents.HOOK_ENCHANTMENTS, hook, player.level());
            }
        }
        else {
            if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "copper")) {
                if (!hookPresent && this.hookInputSlot.getItem().isEmpty()) {
                    ItemStack hookItem = new ItemStack(ModItems.COPPER_FISHING_HOOK.get(), 1);
                    hookItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.HOOK_DURABILITY));
                    applyEnchantments(hookItem, player, rod.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.hookInputSlot.index, hookItem);
                    hookPresent = true;
                }
            }

            if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "prismarine")) {
                if (!hookPresent && this.hookInputSlot.getItem().isEmpty()) {
                    ItemStack hookItem = new ItemStack(ModItems.PRISMARINE_FISHING_HOOK.get(), 1);
                    hookItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.HOOK_DURABILITY));
                    applyEnchantments(hookItem, player, rod.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.hookInputSlot.index, hookItem);
                    hookPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "iron")) {
                if (!hookPresent && this.hookInputSlot.getItem().isEmpty()) {
                    ItemStack hookItem = new ItemStack(ModItems.IRON_FISHING_HOOK.get(), 1);
                    hookItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.HOOK_DURABILITY));
                    applyEnchantments(hookItem, player, rod.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.hookInputSlot.index, hookItem);
                    hookPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "diamond")) {
                if (!hookPresent && this.hookInputSlot.getItem().isEmpty()) {
                    ItemStack hookItem = new ItemStack(ModItems.DIAMOND_FISHING_HOOK.get(), 1);
                    hookItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.HOOK_DURABILITY));
                    applyEnchantments(hookItem, player, rod.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.hookInputSlot.index, hookItem);
                    hookPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "netherite")) {
                if (!hookPresent && this.hookInputSlot.getItem().isEmpty()) {
                    ItemStack hookItem = new ItemStack(ModItems.NETHERITE_FISHING_HOOK.get(), 1);
                    hookItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.HOOK_DURABILITY));
                    applyEnchantments(hookItem, player, rod.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.hookInputSlot.index, hookItem);
                    hookPresent = true;
                }
            }
            if (!Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "none")) {
                rod.set(ModComponents.HOOK_COMPONENT, "none");
            }
        }
        // lines
        if (!line.isEmpty()) {
            if (line.getItem() == ModItems.COPPER_LACED_FISHING_LINE.get() && !Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "copper")) {
                rod.set(ModComponents.LINE_COMPONENT, "copper");
                rod.set(ModComponents.LINE_DURABILITY, line.getDamageValue());
                captureEnchantments(rod, ModComponents.LINE_ENCHANTMENTS, line, player.level());
            }
            if (line.getItem() == ModItems.PRISMARINE_LACED_FISHING_LINE.get() && !Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "prismarine")) {
                rod.set(ModComponents.LINE_COMPONENT, "prismarine");
                rod.set(ModComponents.LINE_DURABILITY, line.getDamageValue());
                captureEnchantments(rod, ModComponents.LINE_ENCHANTMENTS, line, player.level());
            }
            if (line.getItem() == ModItems.IRON_LACED_FISHING_LINE.get() && !Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "iron")) {
                rod.set(ModComponents.LINE_COMPONENT, "iron");
                rod.set(ModComponents.LINE_DURABILITY, line.getDamageValue());
                captureEnchantments(rod, ModComponents.LINE_ENCHANTMENTS, line, player.level());
            }
            if (line.getItem() == ModItems.DIAMOND_LACED_FISHING_LINE.get() && !Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "diamond")) {
                rod.set(ModComponents.LINE_COMPONENT, "diamond");
                rod.set(ModComponents.LINE_DURABILITY, line.getDamageValue());
                captureEnchantments(rod, ModComponents.LINE_ENCHANTMENTS, line, player.level());
            }
            if (line.getItem() == ModItems.NETHERITE_LACED_FISHING_LINE.get() && !Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "netherite")) {
                rod.set(ModComponents.LINE_COMPONENT, "netherite");
                rod.set(ModComponents.LINE_DURABILITY, line.getDamageValue());
                captureEnchantments(rod, ModComponents.LINE_ENCHANTMENTS, line, player.level());
            }
        }
        else {
            if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "copper")) {
                if (!linePresent && this.lineInputSlot.getItem().isEmpty()) {
                    ItemStack lineItem = new ItemStack(ModItems.COPPER_LACED_FISHING_LINE.get(), 1);
                    lineItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.LINE_DURABILITY));
                    applyEnchantments(lineItem, player, rod.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.lineInputSlot.index, lineItem);
                    linePresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "prismarine")) {
                if (!linePresent && this.lineInputSlot.getItem().isEmpty()) {
                    ItemStack lineItem = new ItemStack(ModItems.PRISMARINE_LACED_FISHING_LINE.get(), 1);
                    lineItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.LINE_DURABILITY));
                    applyEnchantments(lineItem, player, rod.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.lineInputSlot.index, lineItem);
                    linePresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "iron")) {
                if (!linePresent && this.lineInputSlot.getItem().isEmpty()) {
                    ItemStack lineItem = new ItemStack(ModItems.IRON_LACED_FISHING_LINE.get(), 1);
                    lineItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.LINE_DURABILITY));
                    applyEnchantments(lineItem, player, rod.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.lineInputSlot.index, lineItem);
                    linePresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "diamond")) {
                if (!linePresent && this.lineInputSlot.getItem().isEmpty()) {
                    ItemStack lineItem = new ItemStack(ModItems.DIAMOND_LACED_FISHING_LINE.get(), 1);
                    lineItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.LINE_DURABILITY));
                    applyEnchantments(lineItem, player, rod.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.lineInputSlot.index, lineItem);
                    linePresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "netherite")) {
                if (!linePresent && this.lineInputSlot.getItem().isEmpty()) {
                    ItemStack lineItem = new ItemStack(ModItems.NETHERITE_LACED_FISHING_LINE.get(), 1);
                    lineItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.LINE_DURABILITY));
                    applyEnchantments(lineItem, player, rod.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.lineInputSlot.index, lineItem);
                    linePresent = true;
                }
            }
            if (!Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "none")) {
                rod.set(ModComponents.LINE_COMPONENT, "none");
            }
        }
        // sinkers
        if (!sinker.isEmpty()) {
            if (sinker.getItem() == ModItems.COPPER_SINKER.get() && !Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "copper")) {
                rod.set(ModComponents.SINKER_COMPONENT, "copper");
                rod.set(ModComponents.SINKER_DURABILITY, sinker.getDamageValue());
                captureEnchantments(rod, ModComponents.SINKER_ENCHANTMENTS, sinker, player.level());
            }
            if (sinker.getItem() == ModItems.PRISMARINE_SINKER.get() && !Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "prismarine")) {
                rod.set(ModComponents.SINKER_COMPONENT, "prismarine");
                rod.set(ModComponents.SINKER_DURABILITY, sinker.getDamageValue());
                captureEnchantments(rod, ModComponents.SINKER_ENCHANTMENTS, sinker, player.level());
            }
            if (sinker.getItem() == ModItems.IRON_SINKER.get() && !Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "iron")) {
                rod.set(ModComponents.SINKER_COMPONENT, "iron");
                rod.set(ModComponents.SINKER_DURABILITY, sinker.getDamageValue());
                captureEnchantments(rod, ModComponents.SINKER_ENCHANTMENTS, sinker, player.level());
            }
            if (sinker.getItem() == ModItems.DIAMOND_SINKER.get() && !Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "diamond")) {
                rod.set(ModComponents.SINKER_COMPONENT, "diamond");
                rod.set(ModComponents.SINKER_DURABILITY, sinker.getDamageValue());
                captureEnchantments(rod, ModComponents.SINKER_ENCHANTMENTS, sinker, player.level());
            }
            if (sinker.getItem() == ModItems.NETHERITE_SINKER.get() && !Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "netherite")) {
                rod.set(ModComponents.SINKER_COMPONENT, "netherite");
                rod.set(ModComponents.SINKER_DURABILITY, sinker.getDamageValue());
                captureEnchantments(rod, ModComponents.SINKER_ENCHANTMENTS, sinker, player.level());
            }
        }
        else {
            if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "copper")) {
                if (!sinkerPresent && this.sinkerInputSlot.getItem().isEmpty()) {
                    ItemStack sinkerItem = new ItemStack(ModItems.COPPER_SINKER.get(), 1);
                    sinkerItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.SINKER_DURABILITY));
                    applyEnchantments(sinkerItem, player, rod.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.sinkerInputSlot.index, sinkerItem);
                    sinkerPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "prismarine")) {
                if (!sinkerPresent && this.sinkerInputSlot.getItem().isEmpty()) {
                    ItemStack sinkerItem = new ItemStack(ModItems.PRISMARINE_SINKER.get(), 1);
                    sinkerItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.SINKER_DURABILITY));
                    applyEnchantments(sinkerItem, player, rod.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.sinkerInputSlot.index, sinkerItem);
                    sinkerPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "iron")) {
                if (!sinkerPresent && this.sinkerInputSlot.getItem().isEmpty()) {
                    ItemStack sinkerItem = new ItemStack(ModItems.IRON_SINKER.get(), 1);
                    sinkerItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.SINKER_DURABILITY));
                    applyEnchantments(sinkerItem, player, rod.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.sinkerInputSlot.index, sinkerItem);
                    sinkerPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "diamond")) {
                if (!sinkerPresent && this.sinkerInputSlot.getItem().isEmpty()) {
                    ItemStack sinkerItem = new ItemStack(ModItems.DIAMOND_SINKER.get(), 1);
                    sinkerItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.SINKER_DURABILITY));
                    applyEnchantments(sinkerItem, player, rod.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.sinkerInputSlot.index, sinkerItem);
                    sinkerPresent = true;
                }
            }
            if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "netherite")) {
                if (!sinkerPresent && this.sinkerInputSlot.getItem().isEmpty()) {
                    ItemStack sinkerItem = new ItemStack(ModItems.NETHERITE_SINKER.get(), 1);
                    sinkerItem.setDamageValue(getIntComponentOrDefault(rod, ModComponents.SINKER_DURABILITY));
                    applyEnchantments(sinkerItem, player, rod.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()));
                    this.inputContainer.setItem(this.sinkerInputSlot.index, sinkerItem);
                    sinkerPresent = true;
                }
            }
            if (!Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "none")) {
                rod.set(ModComponents.SINKER_COMPONENT, "none");
            }
        }
    }

    @Override
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> {
            ItemStack hookStack = this.hookInputSlot.getItem();
            if (!hookStack.isEmpty()) this.inputContainer.removeItemNoUpdate(this.hookInputSlot.index);

            ItemStack lineStack = this.lineInputSlot.getItem();
            if (!lineStack.isEmpty()) this.inputContainer.removeItemNoUpdate(this.lineInputSlot.index);

            ItemStack sinkerStack = this.sinkerInputSlot.getItem();
            if (!sinkerStack.isEmpty()) this.inputContainer.removeItemNoUpdate(this.sinkerInputSlot.index);

            this.clearContainer(player, this.inputContainer);
        });
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, ModBlocks.ANGLING_TABLE.get());
    }

    private static int getUnbreakingLevel(Level level, ItemStack stack) {
        return EnchantmentHelper.getItemEnchantmentLevel(
                level.registryAccess().lookupOrThrow(Enchantments.UNBREAKING.registryKey()).getOrThrow(Enchantments.UNBREAKING),
                stack
        );
    }

    private static int getIntComponentOrDefault(ItemStack stack, DataComponentType<Integer> component) {
        Integer val = stack.get(component);
        return val != null ? val : 0;
    }

    private static void captureEnchantments(ItemStack rod, DataComponentType<List<String>> enchantmentsComp, ItemStack part, Level level) {
        List<String> enchantments = new ArrayList<>();
        if (EnchantmentHelper.has(part, EnchantmentEffectComponents.REPAIR_WITH_XP)) enchantments.add(ModComponents.MENDING_ID);
        if (EnchantmentHelper.has(part, EnchantmentEffectComponents.PREVENT_EQUIPMENT_DROP)) enchantments.add(ModComponents.VANISHING_ID);
        if (EnchantmentHelper.has(part, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE)) enchantments.add(ModComponents.BINDING_ID);
        int unbreaking = getUnbreakingLevel(level, part);
        if (unbreaking > 0) enchantments.add(ModComponents.UNBREAKING_ID + ":" + unbreaking);
        rod.set(enchantmentsComp, enchantments);
    }

    private static void applyEnchantments(ItemStack part, Player player, List<String> enchantments) {
        for (String entry : enchantments) {
            String id = entry;
            int level = 1;
            int lastColon = entry.lastIndexOf(':');
            if (lastColon >= 0) {
                String suffix = entry.substring(lastColon + 1);
                try {
                    level = Integer.parseInt(suffix);
                    id = entry.substring(0, lastColon);
                } catch (NumberFormatException ignored) {
                }
            }
            ResourceKey<Enchantment> key = switch (id) {
                case ModComponents.MENDING_ID -> Enchantments.MENDING;
                case ModComponents.VANISHING_ID -> Enchantments.VANISHING_CURSE;
                case ModComponents.BINDING_ID -> Enchantments.BINDING_CURSE;
                case ModComponents.UNBREAKING_ID -> Enchantments.UNBREAKING;
                default -> null;
            };
            if (key != null && level > 0) {
                part.enchant(player.level().registryAccess().lookupOrThrow(key.registryKey()).getOrThrow(key), level);
            }
        }
    }

    private static boolean hasEnchantment(ItemStack rod, DataComponentType<List<String>> enchantmentsComp, String id) {
        List<String> list = rod.getOrDefault(enchantmentsComp, List.of());
        for (String e : list) {
            if (e.equals(id) || e.startsWith(id + ":")) return true;
        }
        return false;
    }
}
