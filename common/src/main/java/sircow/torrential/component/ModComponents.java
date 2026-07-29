package sircow.torrential.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import sircow.torrential.Constants;

public class ModComponents {
    public static final DataComponentType<Integer> HOOK_DURABILITY = DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build();
    public static final DataComponentType<String> HOOK_COMPONENT = DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build();
    public static final DataComponentType<Integer> HOOK_UNBREAKING = DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build();
    public static final DataComponentType<Integer> LINE_DURABILITY = DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build();
    public static final DataComponentType<String> LINE_COMPONENT = DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build();
    public static final DataComponentType<Integer> LINE_UNBREAKING = DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build();
    public static final DataComponentType<Integer> SINKER_DURABILITY = DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build();
    public static final DataComponentType<String> SINKER_COMPONENT = DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build();
    public static final DataComponentType<Integer> SINKER_UNBREAKING = DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build();

    public static void registerModComponents() {
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("hook_durability"), HOOK_DURABILITY);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("hook_component"), HOOK_COMPONENT);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("hook_unbreaking"), HOOK_UNBREAKING);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("line_durability"), LINE_DURABILITY);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("line_component"), LINE_COMPONENT);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("line_unbreaking"), LINE_UNBREAKING);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("sinker_durability"), SINKER_DURABILITY);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("sinker_component"), SINKER_COMPONENT);
        Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Constants.id("sinker_unbreaking"), SINKER_UNBREAKING);
    }
}
