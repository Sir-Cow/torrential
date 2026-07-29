package sircow.torrential.trigger;

import net.minecraft.advancements.triggers.CriterionTrigger;

import java.util.function.Supplier;

public class ModTriggers {
    public static final TriggerEntry<CustomTrigger> CONDUIT_POWER = new TriggerEntry<>("conduit_power", CustomTrigger::new);
    public static final TriggerEntry<CustomTrigger> CONDUIT_POWER_FULL = new TriggerEntry<>("conduit_power_full", CustomTrigger::new);
    public static final TriggerEntry<CustomTrigger> FISH_ON_NAUTILUS = new TriggerEntry<>("fish_on_nautilus", CustomTrigger::new);

    public static class TriggerEntry<T extends CriterionTrigger<?>> {
        public final String id;
        public final Supplier<T> factory;
        private Supplier<T> trigger;

        public TriggerEntry(String id, Supplier<T> factory) {
            this.id = id;
            this.factory = factory;
        }

        public void bind(Supplier<T> supplier) {
            this.trigger = supplier;
        }

        public T get() {
            return this.trigger.get();
        }
    }
}
