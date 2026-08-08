package sircow.torrential.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import sircow.torrential.Constants;
import sircow.torrential.menu.AnglingTableMenu;

public class AnglingTableScreen extends AbstractContainerScreen<AnglingTableMenu> {
    private static final ResourceLocation BG_LOCATION = Constants.id("textures/gui/container/angling_table_gui.png");
    private static final ResourceLocation ROD_SLOT_TEXTURE = Constants.id("container/slot/fishing_rod");
    private static final ResourceLocation HOOK_SLOT_TEXTURE = Constants.id("container/slot/hook");
    private static final ResourceLocation LINE_SLOT_TEXTURE = Constants.id("container/slot/line");
    private static final ResourceLocation SINKER_SLOT_TEXTURE = Constants.id("container/slot/sinker");

    public AnglingTableScreen(AnglingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(BG_LOCATION, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        renderEmptySlotIcons(graphics, this.leftPos, this.topPos);
    }

    private void renderEmptySlotIcons(GuiGraphics graphics, int x, int y) {
        graphics.blitSprite(ROD_SLOT_TEXTURE, x + 79, y + 17, 16, 16);
        graphics.blitSprite(HOOK_SLOT_TEXTURE, x + 56, y + 51, 16, 16);
        graphics.blitSprite(LINE_SLOT_TEXTURE, x + 79, y + 58, 16, 16);
        graphics.blitSprite(SINKER_SLOT_TEXTURE, x + 102, y + 51, 16, 16);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
