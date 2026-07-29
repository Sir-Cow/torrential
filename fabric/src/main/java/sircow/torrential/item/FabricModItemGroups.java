package sircow.torrential.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import sircow.torrential.Constants;
import sircow.torrential.block.ModBlocks;

public class FabricModItemGroups {
    public static final ResourceKey<CreativeModeTab> TORRENTIAL_TAB_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.id("torrential"));
    public static CreativeModeTab TORRENTIAL_GROUP;

    public static void register() {
        TORRENTIAL_GROUP = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                .title(Component.translatable("itemgroup.torrential.items"))
                .icon(() -> new ItemStack(ModItems.AQUATIC_FIBER))
                .displayItems((displayContext, entries) -> {
                    entries.accept(ModBlocks.ANGLING_TABLE.asItem());
                    entries.accept(ModItems.AQUATIC_FIBER);
                    entries.accept(ModItems.CACHE);
                    entries.accept(ModItems.COPPER_FISHING_HOOK);
                    entries.accept(ModItems.IRON_FISHING_HOOK);
                    entries.accept(ModItems.PRISMARINE_FISHING_HOOK);
                    entries.accept(ModItems.DIAMOND_FISHING_HOOK);
                    entries.accept(ModItems.NETHERITE_FISHING_HOOK);
                    entries.accept(ModItems.COPPER_LACED_FISHING_LINE);
                    entries.accept(ModItems.IRON_LACED_FISHING_LINE);
                    entries.accept(ModItems.PRISMARINE_LACED_FISHING_LINE);
                    entries.accept(ModItems.DIAMOND_LACED_FISHING_LINE);
                    entries.accept(ModItems.NETHERITE_LACED_FISHING_LINE);
                    entries.accept(ModItems.COPPER_SINKER);
                    entries.accept(ModItems.IRON_SINKER);
                    entries.accept(ModItems.PRISMARINE_SINKER);
                    entries.accept(ModItems.DIAMOND_SINKER);
                    entries.accept(ModItems.NETHERITE_SINKER);
                })
                .build();
        registerCreativeTab(TORRENTIAL_GROUP);
    }

    private static void registerCreativeTab(CreativeModeTab tab){
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FabricModItemGroups.TORRENTIAL_TAB_KEY, tab);
    }

    public static void registerItemGroups() {
        register();
    }
}
