package sircow.torrential.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import sircow.torrential.Constants;
import sircow.torrential.screen.AnglingTableScreen;
import sircow.torrential.screen.CacheScreen;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class NeoForgeTorrentialClient {
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(Constants.ANGLING_TABLE_MENU_TYPE.get(), AnglingTableScreen::new);
        event.register(Constants.CACHE_MENU_TYPE.get(), CacheScreen::new);
    }
}
