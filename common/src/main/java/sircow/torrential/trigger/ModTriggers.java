package sircow.torrential.trigger;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.Identifier;
import sircow.torrential.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModTriggers {
    private static final Map<Identifier, CriterionTrigger<?>> TRIGGERS = new LinkedHashMap<>();

    public static final CustomTrigger CONDUIT_POWER = register("conduit_power", new CustomTrigger());
    public static final CustomTrigger CONDUIT_POWER_FULL = register("conduit_power_full", new CustomTrigger());
    public static final CustomTrigger FISH_ON_NAUTILUS = register("fish_on_nautilus", new CustomTrigger());

    private static <T extends CriterionTrigger<?>> T register(String name, T trigger) {
        TRIGGERS.put(Constants.id(name), trigger);
        return trigger;
    }

    public static Map<Identifier, CriterionTrigger<?>> getTriggers() {
        return TRIGGERS;
    }
}
