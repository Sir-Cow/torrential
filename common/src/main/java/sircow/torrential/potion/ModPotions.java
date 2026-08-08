package sircow.torrential.potion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import sircow.torrential.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModPotions {
    private static final Map<ResourceLocation, Potion> POTIONS = new LinkedHashMap<>();

    public static final Potion LUCK = register("luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600)));
    public static final Potion LONG_LUCK = register("long_luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 9600)));
    public static final Potion STRONG_LUCK = register("strong_luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600, 1)));

    private static Potion register(String name, Potion potion) {
        POTIONS.put(Constants.id(name), potion);
        return potion;
    }

    public static Holder.Reference<Potion> luckHolder() {
        return BuiltInRegistries.POTION.getHolder(Constants.id("luck")).orElseThrow();
    }

    public static Holder.Reference<Potion> longLuckHolder() {
        return BuiltInRegistries.POTION.getHolder(Constants.id("long_luck")).orElseThrow();
    }

    public static Holder.Reference<Potion> strongLuckHolder() {
        return BuiltInRegistries.POTION.getHolder(Constants.id("strong_luck")).orElseThrow();
    }

    public static Map<ResourceLocation, Potion> getPotions() {
        return POTIONS;
    }
}
