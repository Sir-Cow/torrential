package sircow.torrential.trigger;

import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import sircow.torrential.Constants;

public class FabricModTriggers {
    public static void registerFabricModTriggers() {
        register(ModTriggers.CONDUIT_POWER);
        register(ModTriggers.CONDUIT_POWER_FULL);
        register(ModTriggers.FISH_ON_NAUTILUS);
    }

    private static <T extends CriterionTrigger<?>> void register(ModTriggers.TriggerEntry<T> entry) {
        var registered = Registry.register(BuiltInRegistries.TRIGGER_TYPES, Constants.id(entry.id), entry.factory.get());
        entry.bind(() -> registered);
    }
}
