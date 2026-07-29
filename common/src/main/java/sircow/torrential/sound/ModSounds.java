package sircow.torrential.sound;

import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import sircow.torrential.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModSounds {
    private static final Map<Identifier, SoundEvent> SOUNDS = new LinkedHashMap<>();

    public static final SoundEvent CACHE_OPEN = register("cache_open");
    public static final SoundEvent CACHE_CLOSE = register("cache_close");

    private static SoundEvent register(String name) {
        return register(Constants.id(name));
    }

    private static SoundEvent register(Identifier name) {
        return register(name, name);
    }

    private static SoundEvent register(Identifier name, Identifier location) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(location);
        SOUNDS.put(name, soundEvent);
        return soundEvent;
    }

    public static Map<Identifier, SoundEvent> getSounds() {
        return SOUNDS;
    }
}
