package sircow.torrential.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import sircow.torrential.Constants;
import sircow.torrential.component.ModComponents;
import sircow.torrential.component.RodTooltipComponent;

public class RodTooltipComponentRenderer implements ClientTooltipComponent {
    private final RodTooltipComponent component;
    private static final ResourceLocation SLOT = Constants.id("container/slot/slot_background");
    private static final ResourceLocation HOOK_SLOT_TEXTURE = Constants.id("container/slot/hook");
    private static final ResourceLocation LINE_SLOT_TEXTURE = Constants.id("container/slot/line");
    private static final ResourceLocation SINKER_SLOT_TEXTURE = Constants.id("container/slot/sinker");

    public RodTooltipComponentRenderer(RodTooltipComponent component) {
        this.component = component;
    }

    private enum RodPart {
        HOOK,
        LINE,
        SINKER
    }

    @Override
    public int getHeight() {
        return 24;
    }

    @Override
    public int getWidth(@NotNull Font font) {
        return 72;
    }

    @Override
    public void renderImage(@NotNull Font font, int x, int y, @NotNull GuiGraphics graphics) {
        renderSlot(graphics, component.hook(), x, y, RodPart.HOOK);
        renderSlot(graphics, component.line(), x + 24, y, RodPart.LINE);
        renderSlot(graphics, component.sinker(), x + 48, y, RodPart.SINKER);
    }

    private void renderSlot(GuiGraphics graphics, ItemStack stack, int x, int y, RodPart part) {
        graphics.blitSprite(SLOT, x, y, 24, 24);

        if (stack.isEmpty()) {
            ResourceLocation texture = switch (part) {
                case HOOK -> HOOK_SLOT_TEXTURE;
                case LINE -> LINE_SLOT_TEXTURE;
                case SINKER -> SINKER_SLOT_TEXTURE;
            };

            graphics.blitSprite(texture, x + 4, y + 4, 16, 16);
        }
        else {
            graphics.renderItem(stack, x + 4, y + 4);
            graphics.renderItemDecorations(Minecraft.getInstance().font, stack, x + 4, y + 4);
        }

        renderDurabilityBar(graphics, x, y, part);
    }

    private void renderDurabilityBar(GuiGraphics graphics, int x, int y, RodPart part) {
        ItemStack rod = component.rod();
        int damage = getCurrentDurability(rod, part);
        int max = getMaxDurability(part);

        if (damage <= 0 && max > 0) return;
        if (max <= 0) return;

        float remainingRatio = Math.max(0.0F, (float)(max - damage) / (float)max);
        int width = Math.round(13.0F * remainingRatio);
        int color = getDurabilityColor(remainingRatio);
        int left = x + 5;
        int top = y + 18;

        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 500);
        graphics.fill(left, top, left + 13, top + 2, 0xFF000000);
        graphics.fill(left, top, left + width, top + 1, color | 0xFF000000);
        graphics.pose().popPose();
    }

    private int getCurrentDurability(ItemStack rod, RodPart part) {
        return switch (part) {
            case HOOK -> rod.getOrDefault(ModComponents.HOOK_DURABILITY, -1);
            case LINE -> rod.getOrDefault(ModComponents.LINE_DURABILITY, -1);
            case SINKER -> rod.getOrDefault(ModComponents.SINKER_DURABILITY, -1);
        };
    }

    private int getDurabilityColor(float remainingRatio) {
        return Mth.hsvToRgb(remainingRatio / 3.0F, 1.0F, 1.0F);
    }

    private int getMaxDurability(RodPart part) {
        ItemStack stack = switch (part) {
            case HOOK -> component.hook();
            case LINE -> component.line();
            case SINKER -> component.sinker();
        };
        return stack.getMaxDamage();
    }
}
