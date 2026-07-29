package sircow.torrential;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.menu.v1.ExtendedMenuType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;
import sircow.torrential.block.FabricModBlocks;
import sircow.torrential.codec.BlockData;
import sircow.torrential.codec.ItemData;
import sircow.torrential.component.FabricModComponents;
import sircow.torrential.item.FabricModItemGroups;
import sircow.torrential.item.FabricModItems;
import sircow.torrential.menu.AnglingTableMenu;
import sircow.torrential.menu.CacheMenu;
import sircow.torrential.potion.FabricModPotions;
import sircow.torrential.sound.FabricModSounds;
import sircow.torrential.trigger.FabricModTriggers;

public class FabricTorrential implements ModInitializer {
    private static final MenuType<AnglingTableMenu> ANGLING_TABLE_MENU_TYPE =
            Registry.register(BuiltInRegistries.MENU, Constants.id("angling_table"),
                    new ExtendedMenuType<>((pWindowID, pInventory, pData) -> new AnglingTableMenu(pWindowID, pInventory), BlockData.CODEC));
    private static final MenuType<CacheMenu> CACHE_MENU_TYPE =
            Registry.register(BuiltInRegistries.MENU, Constants.id("cache"),
                    new ExtendedMenuType<>(CacheMenu::new, ItemData.CODEC));

    static {
        Constants.ANGLING_TABLE_MENU_TYPE = () -> ANGLING_TABLE_MENU_TYPE;
        Constants.CACHE_MENU_TYPE = () -> CACHE_MENU_TYPE;
    }

    @Override
    public void onInitialize() {
        CommonClass.init();
        FabricModBlocks.registerFabricModBlocks();
        FabricModItems.registerFabricModItems();
        FabricModItemGroups.registerFabricItemGroups();
        FabricModComponents.registerFabricModComponents();
        FabricModSounds.registerFabricModSounds();
        FabricModPotions.registerFabricModPotions();
        FabricModTriggers.registerFabricModTriggers();
    }
}
