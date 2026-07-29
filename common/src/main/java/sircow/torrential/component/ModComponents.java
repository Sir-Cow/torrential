package sircow.torrential.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import sircow.torrential.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModComponents {
    private static final Map<Identifier, DataComponentType<?>> COMPONENTS = new LinkedHashMap<>();

    public static final DataComponentType<Integer> HOOK_DURABILITY = register("hook_durability", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<String> HOOK_COMPONENT = register("hook_component", DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DataComponentType<Integer> HOOK_UNBREAKING = register("hook_unbreaking", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<Integer> LINE_DURABILITY = register("line_durability", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<String> LINE_COMPONENT = register("line_component", DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DataComponentType<Integer> LINE_UNBREAKING = register("line_unbreaking", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<Integer> SINKER_DURABILITY = register("sinker_durability", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<String> SINKER_COMPONENT = register("sinker_component", DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());

    public static final DataComponentType<Integer> SINKER_UNBREAKING = register("sinker_unbreaking",
            DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    private static <T> DataComponentType<T> register(String name, DataComponentType<T> componentType) {
        COMPONENTS.put(Constants.id(name), componentType);
        return componentType;
    }

    public static Map<Identifier, DataComponentType<?>> getComponents() {
        return COMPONENTS;
    }
}
