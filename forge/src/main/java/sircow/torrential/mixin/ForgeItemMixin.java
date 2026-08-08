package sircow.torrential.mixin;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sircow.torrential.codec.ItemData;
import sircow.torrential.container.CacheContainer;
import sircow.torrential.item.custom.CacheItem;
import sircow.torrential.menu.CacheMenu;
import sircow.torrential.sound.ModSounds;

import java.util.Collections;

@Mixin(Item.class)
public class ForgeItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void torrential$openCacheMenu(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if ((Object) this instanceof CacheItem cacheItem) {
            if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
                ItemStack usedStack = player.getItemInHand(hand);
                ItemContainerContents container = usedStack.get(DataComponents.CONTAINER);

                int size = cacheItem.getSize();

                if (container == null) {
                    container = ItemContainerContents.fromItems(Collections.nCopies(size, ItemStack.EMPTY));
                    usedStack.set(DataComponents.CONTAINER, container);
                }

                CacheContainer cacheContainer = new CacheContainer(size, usedStack, container);
                serverPlayer.openMenu(
                        getMenuProvider(cacheContainer, usedStack),
                        buf -> ItemData.CODEC.encode(
                                RegistryFriendlyByteBuf.decorator(serverPlayer.registryAccess()).apply(buf),
                                new ItemData(size)
                        )
                );
                serverPlayer.level().playSound(null, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), ModSounds.CACHE_OPEN, SoundSource.PLAYERS, 1.0F, 1.0F);

                cir.setReturnValue(InteractionResultHolder.success(usedStack));
            }
        }
    }

    @Unique
    private MenuProvider getMenuProvider(Container container, ItemStack stackContext) {
        return new MenuProvider() {
            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new CacheMenu(syncId, playerInventory, container, stackContext);
            }

            @Override
            public @NotNull Component getDisplayName() {
                return Component.translatable("container.torrential.cache");
            }
        };
    }
}
