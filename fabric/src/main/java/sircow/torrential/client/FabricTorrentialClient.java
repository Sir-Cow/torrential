package sircow.torrential.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import sircow.torrential.Constants;
import sircow.torrential.client.renderer.RodTooltipComponentRenderer;
import sircow.torrential.component.ModComponents;
import sircow.torrential.component.RodTooltipComponent;
import sircow.torrential.item.ModItems;
import sircow.torrential.screen.AnglingTableScreen;
import sircow.torrential.screen.CacheScreen;
import sircow.torrential.tag.ModTags;

import java.util.List;
import java.util.Map;

public class FabricTorrentialClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerMenuScreens();
        registerCustomTooltip();
    }

    private void registerMenuScreens() {
        MenuScreens.register(Constants.ANGLING_TABLE_MENU_TYPE.get(), AnglingTableScreen::new);
        MenuScreens.register(Constants.CACHE_MENU_TYPE.get(), CacheScreen::new);
    }

    private void registerCustomTooltip() {
        TooltipComponentCallback.EVENT.register(data -> {
            if (data instanceof RodTooltipComponent previewData) return new RodTooltipComponentRenderer(previewData);
            return null;
        });

        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) -> {
            String durabilityTranslatable = Component.translatable("item.durability").getString();
            String textBeforeSplit = durabilityTranslatable.contains(":")
                    ? durabilityTranslatable.substring(0, durabilityTranslatable.indexOf(':')).trim()
                    : durabilityTranslatable;
            int insertIndex = findTooltipInsertIndex(lines, textBeforeSplit);
            String hook = stack.get(ModComponents.HOOK_COMPONENT);
            String line = stack.get(ModComponents.LINE_COMPONENT);
            String sinker = stack.get(ModComponents.SINKER_COMPONENT);

            if (stack.is(ModTags.ROD_UPGRADES)) addFishingUpgradeTooltip(lines, insertIndex, stack.getItem());
            if ((hook != null && !hook.equals("none")) || (line != null && !line.equals("none")) || (sinker != null && !sinker.equals("none"))) {
                addFishingUpgradeTooltip(lines, insertIndex, hook, line, sinker);
            }
        });
    }

    private int findTooltipInsertIndex(List<Component> lines, String textBeforeSplit) {
        for (int i = 0; i < lines.size(); i++) {
            String lineString = lines.get(i).getString();
            if (lineString.contains(textBeforeSplit) || (!lineString.contains(textBeforeSplit) && (lineString.contains("minecraft") || lineString.contains("torrential")))) {
                return i;
            }
        }
        return lines.size();
    }

    private void addIfPresent(List<Component> lines, int insertIndex, Item item, Map<Item, Double> map, String translationKey) {
        if (map.containsKey(item)) {
            lines.add(insertIndex, Component.literal(" ").append(Component.translatable(translationKey, map.get(item)).withStyle(ChatFormatting.BLUE)));
        }
    }

    private void addFishingUpgradeTooltip(List<Component> lines, int insertIndex, String hook, String line, String sinker) {
        lines.add(insertIndex++, Component.empty());
        lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.rod_in_hand").withStyle(ChatFormatting.GRAY));

        Map<String, Double> valuesMap = Map.of(
                "copper", 0.5,
                "iron", 1.0,
                "prismarine", 1.5,
                "diamond", 2.0,
                "netherite", 3.0
        );

        if (valuesMap.containsKey(hook)) lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.fishing_speed", valuesMap.get(hook)).withStyle(ChatFormatting.BLUE));
        if (valuesMap.containsKey(line)) lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.fortune", valuesMap.get(line)).withStyle(ChatFormatting.BLUE));
        if (valuesMap.containsKey(sinker)) lines.add(insertIndex, Component.translatable("item.torrential.modifiers.luck", valuesMap.get(sinker)).withStyle(ChatFormatting.BLUE));
    }

    private void addFishingUpgradeTooltip(List<Component> lines, int insertIndex, Item item) {
        lines.add(insertIndex++, Component.empty());
        lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.on_rod").withStyle(ChatFormatting.GRAY));

        Map<Item, Double> fishingSpeedMap = Map.of(
                ModItems.COPPER_FISHING_HOOK.get(), 0.5,
                ModItems.IRON_FISHING_HOOK.get(), 1.0,
                ModItems.PRISMARINE_FISHING_HOOK.get(), 1.5,
                ModItems.DIAMOND_FISHING_HOOK.get(), 2.0,
                ModItems.NETHERITE_FISHING_HOOK.get(), 3.0
        );
        Map<Item, Double> fortuneMap = Map.of(
                ModItems.COPPER_LACED_FISHING_LINE.get(), 0.5,
                ModItems.IRON_LACED_FISHING_LINE.get(), 1.0,
                ModItems.PRISMARINE_LACED_FISHING_LINE.get(), 1.5,
                ModItems.DIAMOND_LACED_FISHING_LINE.get(), 2.0,
                ModItems.NETHERITE_LACED_FISHING_LINE.get(), 3.0
        );
        Map<Item, Double> luckMap = Map.of(
                ModItems.COPPER_SINKER.get(), 0.5,
                ModItems.IRON_SINKER.get(), 1.0,
                ModItems.PRISMARINE_SINKER.get(), 1.5,
                ModItems.DIAMOND_SINKER.get(), 2.0,
                ModItems.NETHERITE_SINKER.get(), 3.0
        );

        addIfPresent(lines, insertIndex, item, fishingSpeedMap, "item.torrential.modifiers.fishing_speed");
        addIfPresent(lines, insertIndex, item, fortuneMap, "item.torrential.modifiers.fortune");
        addIfPresent(lines, insertIndex, item, luckMap, "item.torrential.modifiers.luck");
    }
}
