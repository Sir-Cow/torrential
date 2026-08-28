package sircow.torrential.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import sircow.torrential.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModSounds {
    private static final Map<ResourceLocation, SoundEvent> SOUNDS = new LinkedHashMap<>();

    public static final SoundEvent ANGLING_TABLE_USE = register("angling_table_use");
    public static final SoundEvent CACHE_OPEN = register("cache_open");
    public static final SoundEvent CACHE_CLOSE = register("cache_close");

    private static SoundEvent register(String name) {
        return register(Constants.id(name));
    }

    private static SoundEvent register(ResourceLocation name) {
        return register(name, name);
    }

    private static SoundEvent register(ResourceLocation name, ResourceLocation location) {
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(location);
        SOUNDS.put(name, soundEvent);
        return soundEvent;
    }

    public static Map<ResourceLocation, SoundEvent> getSounds() {
        return SOUNDS;
    }
}
