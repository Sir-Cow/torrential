package sircow.torrential.potion;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import sircow.torrential.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModPotions {
    private static final Map<Identifier, Potion> POTIONS = new LinkedHashMap<>();

    public static final Potion NAUTILUS_BLESSING = register("nautilus_blessing", new Potion("nautilus_blessing", new MobEffectInstance(MobEffects.BREATH_OF_THE_NAUTILUS, 3600)));
    public static final Potion LUCK = register("luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600)));
    public static final Potion LONG_LUCK = register("long_luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 9600)));
    public static final Potion STRONG_LUCK = register("strong_luck", new Potion("luck", new MobEffectInstance(MobEffects.LUCK, 3600, 1)));

    public static final Holder<Potion> NAUTILUS_BLESSING_HOLDER = Holder.direct(NAUTILUS_BLESSING);
    public static final Holder<Potion> LUCK_HOLDER = Holder.direct(LUCK);
    public static final Holder<Potion> LONG_LUCK_HOLDER = Holder.direct(LONG_LUCK);
    public static final Holder<Potion> STRONG_LUCK_HOLDER = Holder.direct(STRONG_LUCK);

    private static Potion register(String name, Potion potion) {
        POTIONS.put(Constants.id(name), potion);
        return potion;
    }

    public static Map<Identifier, Potion> getPotions() {
        return POTIONS;
    }
}
