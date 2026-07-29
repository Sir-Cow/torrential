package sircow.torrential;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import sircow.torrential.codec.ItemData;
import sircow.torrential.item.NeoForgeModItemGroups;
import sircow.torrential.menu.AnglingTableMenu;
import sircow.torrential.menu.CacheMenu;
import sircow.torrential.platform.Services;

@Mod(Constants.MOD_ID)
public class NeoForgeTorrential {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<AnglingTableMenu>> ANGLING_TABLE_MENU_TYPE = MENU_TYPES.register("angling_table", () -> IMenuTypeExtension.create((windowId, inv, data) -> new AnglingTableMenu(windowId, inv)));
    public static final DeferredHolder<MenuType<?>, MenuType<CacheMenu>> CACHE_MENU_TYPE = MENU_TYPES.register("cache", () -> IMenuTypeExtension.create((windowId, inventory, buf) -> new CacheMenu(windowId, inventory, ItemData.CODEC.decode(RegistryFriendlyByteBuf.decorator(inventory.player.registryAccess(), ConnectionType.OTHER).apply(buf)))));

    static {
        Constants.ANGLING_TABLE_MENU_TYPE = ANGLING_TABLE_MENU_TYPE;
        Constants.CACHE_MENU_TYPE = CACHE_MENU_TYPE;
    }

    public NeoForgeTorrential(IEventBus modEventBus, ModContainer container) {
        if (Services.PLATFORM.isModLoaded("pinferno")) {
            throw new IllegalStateException(String.valueOf(Component.translatable("info.torrential.conflict", Constants.MOD_ID, "Preserved: Inferno")));
        }

        MENU_TYPES.register(modEventBus);
        NeoForgeModItemGroups.CREATIVE_MODE_TABS.register(modEventBus);
        CommonClass.init();
    }
}
