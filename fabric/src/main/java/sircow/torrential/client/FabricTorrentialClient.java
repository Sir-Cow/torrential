package sircow.torrential.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ClientTooltipComponentCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import sircow.torrential.Constants;
import sircow.torrential.client.renderer.RodTooltipComponentRenderer;
import sircow.torrential.component.ModComponents;
import sircow.torrential.component.RodTooltipComponent;
import sircow.torrential.config.ConfigManager;
import sircow.torrential.config.FabricConfig;
import sircow.torrential.config.ServerModConfig;
import sircow.torrential.item.ModItems;
import sircow.torrential.screen.AnglingTableScreen;
import sircow.torrential.screen.CacheScreen;
import sircow.torrential.tag.ModTags;

import java.util.List;
import java.util.Map;

public class FabricTorrentialClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FabricConfig.loadClient();
        registerMenuScreens();
        registerCustomTooltip();
    }

    private void registerMenuScreens() {
        MenuScreens.register(Constants.ANGLING_TABLE_MENU_TYPE.get(), AnglingTableScreen::new);
        MenuScreens.register(Constants.CACHE_MENU_TYPE.get(), CacheScreen::new);
    }

    private void registerCustomTooltip() {
        ClientTooltipComponentCallback.EVENT.register(data -> {
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

        ServerModConfig config = ConfigManager.getServer();

        Map<String, Double> hookSpeedMap = Map.of(
                "copper", (double) config.hookSpeedCopper / 100,
                "iron", (double) config.hookSpeedIron / 100,
                "prismarine", (double) config.hookSpeedPrismarine / 100,
                "diamond", (double) config.hookSpeedDiamond / 100,
                "netherite", (double) config.hookSpeedNetherite / 100
        );
        Map<String, Double> lineFortuneMap = Map.of(
                "copper", config.lineFortuneCopper,
                "iron", config.lineFortuneIron,
                "prismarine", config.lineFortunePrismarine,
                "diamond", config.lineFortuneDiamond,
                "netherite", config.lineFortuneNetherite
        );
        Map<String, Double> sinkerLuckMap = Map.of(
                "copper", config.sinkerLuckCopper,
                "iron", config.sinkerLuckIron,
                "prismarine", config.sinkerLuckPrismarine,
                "diamond", config.sinkerLuckDiamond,
                "netherite", config.sinkerLuckNetherite
        );

        if (hookSpeedMap.containsKey(hook)) lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.fishing_speed", hookSpeedMap.get(hook)).withStyle(ChatFormatting.BLUE));
        if (lineFortuneMap.containsKey(line)) lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.fortune", lineFortuneMap.get(line)).withStyle(ChatFormatting.BLUE));
        if (sinkerLuckMap.containsKey(sinker)) lines.add(insertIndex, Component.translatable("item.torrential.modifiers.luck", sinkerLuckMap.get(sinker)).withStyle(ChatFormatting.BLUE));
    }

    private void addFishingUpgradeTooltip(List<Component> lines, int insertIndex, Item item) {
        lines.add(insertIndex++, Component.empty());
        lines.add(insertIndex++, Component.translatable("item.torrential.modifiers.on_rod").withStyle(ChatFormatting.GRAY));

        ServerModConfig config = ConfigManager.getServer();

        Map<Item, Double> fishingSpeedMap = Map.of(
                ModItems.COPPER_FISHING_HOOK.get(), (double) config.hookSpeedCopper / 100,
                ModItems.IRON_FISHING_HOOK.get(), (double) config.hookSpeedIron / 100,
                ModItems.PRISMARINE_FISHING_HOOK.get(), (double) config.hookSpeedPrismarine / 100,
                ModItems.DIAMOND_FISHING_HOOK.get(), (double) config.hookSpeedDiamond / 100,
                ModItems.NETHERITE_FISHING_HOOK.get(), (double) config.hookSpeedNetherite / 100
        );
        Map<Item, Double> fortuneMap = Map.of(
                ModItems.COPPER_LACED_FISHING_LINE.get(), config.lineFortuneCopper,
                ModItems.IRON_LACED_FISHING_LINE.get(), config.lineFortuneIron,
                ModItems.PRISMARINE_LACED_FISHING_LINE.get(), config.lineFortunePrismarine,
                ModItems.DIAMOND_LACED_FISHING_LINE.get(), config.lineFortuneDiamond,
                ModItems.NETHERITE_LACED_FISHING_LINE.get(), config.lineFortuneNetherite
        );
        Map<Item, Double> luckMap = Map.of(
                ModItems.COPPER_SINKER.get(), config.sinkerLuckCopper,
                ModItems.IRON_SINKER.get(), config.sinkerLuckIron,
                ModItems.PRISMARINE_SINKER.get(), config.sinkerLuckPrismarine,
                ModItems.DIAMOND_SINKER.get(), config.sinkerLuckDiamond,
                ModItems.NETHERITE_SINKER.get(), config.sinkerLuckNetherite
        );

        addIfPresent(lines, insertIndex, item, fishingSpeedMap, "item.torrential.modifiers.fishing_speed");
        addIfPresent(lines, insertIndex, item, fortuneMap, "item.torrential.modifiers.fortune");
        addIfPresent(lines, insertIndex, item, luckMap, "item.torrential.modifiers.luck");
    }
}
