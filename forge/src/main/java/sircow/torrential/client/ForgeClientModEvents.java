package sircow.torrential.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sircow.torrential.Constants;
import sircow.torrential.client.renderer.RodTooltipComponentRenderer;
import sircow.torrential.component.RodTooltipComponent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeClientModEvents {
    @SubscribeEvent
    public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(RodTooltipComponent.class, RodTooltipComponentRenderer::new);
    }
}
