package sircow.torrential.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import sircow.torrential.Constants;
import sircow.torrential.block.ModBlocks;

public class ForgeModItemGroups {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TORRENTIAL_GROUP =
            CREATIVE_MODE_TABS.register("torrential", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemgroup.torrential.items"))
                            .icon(() -> new ItemStack(ModItems.AQUATIC_FIBER.get()))
                            .displayItems((displayContext, entries) -> {
                                entries.accept(ModBlocks.ANGLING_TABLE.get().asItem());
                                entries.accept(ModItems.AQUATIC_FIBER.get());
                                entries.accept(ModItems.CACHE.get());
                                entries.accept(ModItems.COPPER_FISHING_HOOK.get());
                                entries.accept(ModItems.IRON_FISHING_HOOK.get());
                                entries.accept(ModItems.PRISMARINE_FISHING_HOOK.get());
                                entries.accept(ModItems.DIAMOND_FISHING_HOOK.get());
                                entries.accept(ModItems.NETHERITE_FISHING_HOOK.get());
                                entries.accept(ModItems.COPPER_LACED_FISHING_LINE.get());
                                entries.accept(ModItems.IRON_LACED_FISHING_LINE.get());
                                entries.accept(ModItems.PRISMARINE_LACED_FISHING_LINE.get());
                                entries.accept(ModItems.DIAMOND_LACED_FISHING_LINE.get());
                                entries.accept(ModItems.NETHERITE_LACED_FISHING_LINE.get());
                                entries.accept(ModItems.COPPER_SINKER.get());
                                entries.accept(ModItems.IRON_SINKER.get());
                                entries.accept(ModItems.PRISMARINE_SINKER.get());
                                entries.accept(ModItems.DIAMOND_SINKER.get());
                                entries.accept(ModItems.NETHERITE_SINKER.get());
                            })
                            .build()
            );
}
