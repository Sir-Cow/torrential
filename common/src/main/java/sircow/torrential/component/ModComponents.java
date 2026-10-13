package sircow.torrential.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import sircow.torrential.Constants;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ModComponents {
    private static final Map<Identifier, DataComponentType<?>> COMPONENTS = new LinkedHashMap<>();

    public static final DataComponentType<Integer> HOOK_DURABILITY = register("hook_durability", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<String> HOOK_COMPONENT = register("hook_component", DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DataComponentType<Integer> LINE_DURABILITY = register("line_durability", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<String> LINE_COMPONENT = register("line_component", DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    public static final DataComponentType<Integer> SINKER_DURABILITY = register("sinker_durability", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    public static final DataComponentType<String> SINKER_COMPONENT = register("sinker_component", DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
    // new system
    public static final DataComponentType<List<String>> HOOK_ENCHANTMENTS = register("hook_enchantments",
            DataComponentType.<List<String>>builder().persistent(Codec.list(Codec.STRING)).networkSynchronized(ByteBufCodecs.<ByteBuf, String>list().apply(ByteBufCodecs.STRING_UTF8)).build());
    public static final DataComponentType<List<String>> LINE_ENCHANTMENTS = register("line_enchantments",
            DataComponentType.<List<String>>builder().persistent(Codec.list(Codec.STRING)).networkSynchronized(ByteBufCodecs.<ByteBuf, String>list().apply(ByteBufCodecs.STRING_UTF8)).build());
    public static final DataComponentType<List<String>> SINKER_ENCHANTMENTS = register("sinker_enchantments",
            DataComponentType.<List<String>>builder().persistent(Codec.list(Codec.STRING)).networkSynchronized(ByteBufCodecs.<ByteBuf, String>list().apply(ByteBufCodecs.STRING_UTF8)).build());

    public static final String UNBREAKING_ID = "minecraft:unbreaking";
    public static final String MENDING_ID = "minecraft:mending";
    public static final String VANISHING_ID = "minecraft:vanishing_curse";
    public static final String BINDING_ID = "minecraft:binding_curse";

    // old system
    /**
     * @deprecated Use {@link #HOOK_ENCHANTMENTS} instead, {@code "minecraft:unbreaking:<level>"}.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated(since = "1.1.0")
    public static final DataComponentType<Integer> HOOK_UNBREAKING = register("hook_unbreaking", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    /**
     * @deprecated Use {@link #LINE_ENCHANTMENTS} instead, {@code "minecraft:unbreaking:<level>"}.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated(since = "1.1.0")
    public static final DataComponentType<Integer> LINE_UNBREAKING = register("line_unbreaking", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());
    /**
     * @deprecated Use {@link #SINKER_ENCHANTMENTS} instead, {@code "minecraft:unbreaking:<level>"}.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated(since = "1.1.0")
    public static final DataComponentType<Integer> SINKER_UNBREAKING = register("sinker_unbreaking", DataComponentType.<Integer>builder().persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT).build());

    private static <T> DataComponentType<T> register(String name, DataComponentType<T> componentType) {
        COMPONENTS.put(Constants.id(name), componentType);
        return componentType;
    }

    public static Map<Identifier, DataComponentType<?>> getComponents() {
        return COMPONENTS;
    }

    public static boolean hasLegacyComponents(ItemStack stack) {
        return (stack.has(HOOK_UNBREAKING) && stack.getOrDefault(HOOK_ENCHANTMENTS, List.of()).isEmpty())
                || (stack.has(LINE_UNBREAKING) && stack.getOrDefault(LINE_ENCHANTMENTS, List.of()).isEmpty())
                || (stack.has(SINKER_UNBREAKING) && stack.getOrDefault(SINKER_ENCHANTMENTS, List.of()).isEmpty());
    }

    public static boolean migrateRod(ItemStack stack) {
        boolean changed = false;
        changed |= migratePart(stack, HOOK_UNBREAKING, HOOK_ENCHANTMENTS);
        changed |= migratePart(stack, LINE_UNBREAKING, LINE_ENCHANTMENTS);
        changed |= migratePart(stack, SINKER_UNBREAKING, SINKER_ENCHANTMENTS);
        return changed;
    }

    private static boolean migratePart(ItemStack stack, DataComponentType<Integer> legacyComp, DataComponentType<List<String>> enchantmentsComp) {
        List<String> list = stack.getOrDefault(enchantmentsComp, List.of());

        if (!list.isEmpty()) {
            if (stack.has(legacyComp)) {
                stack.remove(legacyComp);
                return true;
            }
            return false;
        }

        Integer legacyLevel = stack.get(legacyComp);
        if (legacyLevel == null || legacyLevel <= 0) return false;

        List<String> migrated = new ArrayList<>();

        migrated.add(UNBREAKING_ID + ":" + legacyLevel);
        stack.set(enchantmentsComp, migrated);
        stack.remove(legacyComp);
        return true;
    }

    public static void sendMigrationMessage(Player player) {
        player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.YELLOW)
                .append(Component.literal("Torrential").withStyle(ChatFormatting.AQUA))
                .append(Component.literal("]").withStyle(ChatFormatting.YELLOW))
                .append(Component.literal(" Converted your rod(s) because of changes in the 1.1.0 update.")
                        .withStyle(ChatFormatting.GRAY)
                        .withStyle(style -> style.withClickEvent(new ClickEvent.OpenUrl(URI.create("https://teampreserved.com/torrential/changelog/#110"))))));
    }
}
