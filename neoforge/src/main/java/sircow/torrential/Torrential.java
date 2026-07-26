package sircow.torrential;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import sircow.torrential.platform.Services;

@Mod(Constants.MOD_ID)
public class Torrential {
    public Torrential(IEventBus eventBus) {
        if (Services.PLATFORM.isModLoaded("pinferno")) {
            throw new IllegalStateException("Torrential is incompatible with Preserved: Inferno. Remove one of them.");
        }

        CommonClass.init();
    }
}
