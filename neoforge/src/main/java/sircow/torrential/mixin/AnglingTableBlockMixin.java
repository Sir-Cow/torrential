package sircow.torrential.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sircow.torrential.block.custom.AnglingTableBlock;
import sircow.torrential.menu.AnglingTableMenu;

@Mixin(AnglingTableBlock.class)
public class AnglingTableBlockMixin {
    @Shadow private static final Component CONTAINER_TITLE = Component.translatable("container.torrential.angling_table");

    @Inject(method = "getMenuProvider", at = @At("HEAD"), cancellable = true)
    private void torrential$checkForAnglingTable(BlockState state, Level level, BlockPos pos, CallbackInfoReturnable<MenuProvider> cir) {
        cir.setReturnValue(new MenuProvider() {
            @Override
            public @NotNull AbstractContainerMenu createMenu(int syncId, @NotNull Inventory playerInventory, @NotNull Player player) {
                return new AnglingTableMenu(syncId, playerInventory, ContainerLevelAccess.create(level, pos));
            }

            @Override
            public @NotNull Component getDisplayName() {
                return CONTAINER_TITLE;
            }
        });
    }
}
