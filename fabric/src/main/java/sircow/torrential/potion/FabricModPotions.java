package sircow.torrential.potion;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FabricModPotions {
    public static void registerFabricModPotions() {
        ModPotions.getPotions().forEach((id, potion) ->
                Registry.register(BuiltInRegistries.POTION, id, potion)
        );
    }
}
