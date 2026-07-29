package sircow.torrential.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import sircow.torrential.Constants;

public class ModSounds {
    public static final SoundEvent CACHE_OPEN = register("cache_open");
    public static final SoundEvent CACHE_CLOSE = register("cache_close");

    private static SoundEvent register(String name) {
        return register(Constants.id(name));
    }

    private static SoundEvent register(Identifier name) {
        return register(name, name);
    }

    private static SoundEvent register(Identifier name, Identifier location) {
        return Registry.register(BuiltInRegistries.SOUND_EVENT, name, SoundEvent.createVariableRangeEvent(location));
    }

    public static void registerSounds() {}
}
