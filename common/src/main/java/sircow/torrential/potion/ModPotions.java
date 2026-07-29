package sircow.torrential.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import sircow.torrential.Constants;

public class ModPotions {
    public static final Holder<Potion> NAUTILUS_BLESSING = register("nautilus_blessing", new Potion("nautilus_blessing", new MobEffectInstance(MobEffects.BREATH_OF_THE_NAUTILUS, 3600)));
    public static final Holder<Potion> LUCK = register("luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600)));
    public static final Holder<Potion> LONG_LUCK = register("long_luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 9600)));
    public static final Holder<Potion> STRONG_LUCK = register("strong_luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600, 1)));

    private static Holder<Potion> register(String name, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Constants.id(name), potion);
    }

    public static void registerModPotions() {}
}
