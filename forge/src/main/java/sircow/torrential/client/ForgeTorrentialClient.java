package sircow.torrential.client;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import sircow.torrential.Constants;
import sircow.torrential.screen.AnglingTableScreen;
import sircow.torrential.screen.CacheScreen;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeTorrentialClient {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(Constants.ANGLING_TABLE_MENU_TYPE.get(), AnglingTableScreen::new);
            MenuScreens.register(Constants.CACHE_MENU_TYPE.get(), CacheScreen::new);
        });
    }
}
