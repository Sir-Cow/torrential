package sircow.torrential;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import sircow.torrential.codec.ItemData;
import sircow.torrential.item.ForgeModItemGroups;
import sircow.torrential.menu.AnglingTableMenu;
import sircow.torrential.menu.CacheMenu;
import sircow.torrential.platform.Services;

@Mod(Constants.MOD_ID)
public class ForgeTorrential {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, Constants.MOD_ID);

    public static final RegistryObject<MenuType<AnglingTableMenu>> ANGLING_TABLE_MENU_TYPE = MENU_TYPES.register("angling_table", () -> IForgeMenuType.create((windowId, inv, data) -> new AnglingTableMenu(windowId, inv)));
    public static final RegistryObject<MenuType<CacheMenu>> CACHE_MENU_TYPE = MENU_TYPES.register("cache", () -> IForgeMenuType.create((windowId, inventory, buf) -> new CacheMenu(windowId, inventory, ItemData.CODEC.decode(RegistryFriendlyByteBuf.decorator(inventory.player.registryAccess()).apply(buf)))));

    static {
        Constants.ANGLING_TABLE_MENU_TYPE = ANGLING_TABLE_MENU_TYPE;
        Constants.CACHE_MENU_TYPE = CACHE_MENU_TYPE;
    }

    public ForgeTorrential(FMLJavaModLoadingContext context) {
        if (Services.PLATFORM.isModLoaded("pinferno")) {
            throw new IllegalStateException(String.valueOf(Component.translatable("info.torrential.conflict", Constants.MOD_ID, "Preserved: Inferno")));
        }

        MENU_TYPES.register(context.getModEventBus());
        ForgeModItemGroups.CREATIVE_MODE_TABS.register(context.getModEventBus());
        CommonClass.init();
    }
}
