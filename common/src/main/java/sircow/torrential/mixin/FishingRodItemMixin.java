package sircow.torrential.mixin;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sircow.torrential.component.ModComponents;
import sircow.torrential.item.ModItems;

import java.util.List;
import java.util.Objects;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    @Inject(method = "use", at = @At("HEAD"))
    private void torrential$onUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (level.isClientSide()) return;
        if (player.gameMode() == GameType.CREATIVE) return;

        ItemStack rod = player.getItemInHand(hand);

        if (player.fishing == null) {
            torrential$apply(rod, player, hand, "hook");
            torrential$apply(rod, player, hand, "line");
            torrential$apply(rod, player, hand, "sinker");
            player.causeFoodExhaustion(0.2F);
        }
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FishingHook;retrieve(Lnet/minecraft/world/item/ItemStack;)I"))
    private int torrential$cancelRetrieveDamage(FishingHook hook, ItemStack rod) {
        hook.retrieve(rod);
        return 0;
    }

    @Unique
    private void torrential$apply(ItemStack rod, Player player, InteractionHand hand, String type) {
        if (Objects.equals(rod.get(torrential$getComponent(type)), "none")) return;

        switch (type) {
            case "hook" -> {
                if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "copper"))
                    update(rod, ModComponents.HOOK_COMPONENT, ModComponents.HOOK_DURABILITY, ModComponents.HOOK_ENCHANTMENTS, ModItems.COPPER_FISHING_HOOK.get(), "copper", player, hand);
                if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "iron"))
                    update(rod, ModComponents.HOOK_COMPONENT, ModComponents.HOOK_DURABILITY, ModComponents.HOOK_ENCHANTMENTS, ModItems.IRON_FISHING_HOOK.get(), "iron", player, hand);
                if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "prismarine"))
                    update(rod, ModComponents.HOOK_COMPONENT, ModComponents.HOOK_DURABILITY, ModComponents.HOOK_ENCHANTMENTS, ModItems.PRISMARINE_FISHING_HOOK.get(), "prismarine", player, hand);
                if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "diamond"))
                    update(rod, ModComponents.HOOK_COMPONENT, ModComponents.HOOK_DURABILITY, ModComponents.HOOK_ENCHANTMENTS, ModItems.DIAMOND_FISHING_HOOK.get(), "diamond", player, hand);
                if (Objects.equals(rod.get(ModComponents.HOOK_COMPONENT), "netherite"))
                    update(rod, ModComponents.HOOK_COMPONENT, ModComponents.HOOK_DURABILITY, ModComponents.HOOK_ENCHANTMENTS, ModItems.NETHERITE_FISHING_HOOK.get(), "netherite", player, hand);
            }

            case "line" -> {
                if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "copper"))
                    update(rod, ModComponents.LINE_COMPONENT, ModComponents.LINE_DURABILITY, ModComponents.LINE_ENCHANTMENTS, ModItems.COPPER_LACED_FISHING_LINE.get(), "copper", player, hand);
                if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "iron"))
                    update(rod, ModComponents.LINE_COMPONENT, ModComponents.LINE_DURABILITY, ModComponents.LINE_ENCHANTMENTS, ModItems.IRON_LACED_FISHING_LINE.get(), "iron", player, hand);
                if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "prismarine"))
                    update(rod, ModComponents.LINE_COMPONENT, ModComponents.LINE_DURABILITY, ModComponents.LINE_ENCHANTMENTS, ModItems.PRISMARINE_LACED_FISHING_LINE.get(), "prismarine", player, hand);
                if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "diamond"))
                    update(rod, ModComponents.LINE_COMPONENT, ModComponents.LINE_DURABILITY, ModComponents.LINE_ENCHANTMENTS, ModItems.DIAMOND_LACED_FISHING_LINE.get(), "diamond", player, hand);
                if (Objects.equals(rod.get(ModComponents.LINE_COMPONENT), "netherite"))
                    update(rod, ModComponents.LINE_COMPONENT, ModComponents.LINE_DURABILITY, ModComponents.LINE_ENCHANTMENTS, ModItems.NETHERITE_LACED_FISHING_LINE.get(), "netherite", player, hand);
            }

            case "sinker" -> {
                if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "copper"))
                    update(rod, ModComponents.SINKER_COMPONENT, ModComponents.SINKER_DURABILITY, ModComponents.SINKER_ENCHANTMENTS, ModItems.COPPER_SINKER.get(), "copper", player, hand);
                if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "iron"))
                    update(rod, ModComponents.SINKER_COMPONENT, ModComponents.SINKER_DURABILITY, ModComponents.SINKER_ENCHANTMENTS, ModItems.IRON_SINKER.get(), "iron", player, hand);
                if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "prismarine"))
                    update(rod, ModComponents.SINKER_COMPONENT, ModComponents.SINKER_DURABILITY, ModComponents.SINKER_ENCHANTMENTS, ModItems.PRISMARINE_SINKER.get(), "prismarine", player, hand);
                if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "diamond"))
                    update(rod, ModComponents.SINKER_COMPONENT, ModComponents.SINKER_DURABILITY, ModComponents.SINKER_ENCHANTMENTS, ModItems.DIAMOND_SINKER.get(), "diamond", player, hand);
                if (Objects.equals(rod.get(ModComponents.SINKER_COMPONENT), "netherite"))
                    update(rod, ModComponents.SINKER_COMPONENT, ModComponents.SINKER_DURABILITY, ModComponents.SINKER_ENCHANTMENTS, ModItems.NETHERITE_SINKER.get(), "netherite", player, hand);
            }
        }
    }

    @Unique
    private void update(ItemStack stack, DataComponentType<String> componentKey, DataComponentType<Integer> durabilityKey, DataComponentType<List<String>> enchantmentsKey, Item durabilityItem, String material, Player player, InteractionHand hand) {
        if (!Objects.equals(stack.get(componentKey), material)) return;

        int current = stack.getOrDefault(durabilityKey, 0);
        int max = durabilityItem.getDefaultInstance().getMaxDamage();
        int unbreaking = torrential$getUnbreakingLevel(stack, enchantmentsKey);

        if (unbreaking > 0) {
            float chance = 1.0F / (unbreaking + 1);
            if (player.level().getRandom().nextFloat() >= chance) return;
        }

        current++;

        if (current >= max) {
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BREAK, player.getSoundSource(), 1.0F, 1.0F);

            player.swing(hand, true);

            if (player instanceof ServerPlayer serverPlayer) serverPlayer.connection.send(new ClientboundAnimatePacket(player, 3));

            stack.set(componentKey, "none");
            stack.set(durabilityKey, 0);
        }
        else stack.set(durabilityKey, current);
    }

    @Unique
    private static int torrential$getUnbreakingLevel(ItemStack stack, DataComponentType<List<String>> enchantmentsKey) {
        List<String> list = stack.getOrDefault(enchantmentsKey, List.of());
        for (String entry : list) {
            if (entry.equals(ModComponents.UNBREAKING_ID)) return 1;
            if (entry.startsWith(ModComponents.UNBREAKING_ID + ":")) {
                try {
                    return Integer.parseInt(entry.substring(ModComponents.UNBREAKING_ID.length() + 1));
                }
                catch (NumberFormatException ignored) {}
            }
        }
        return 0;
    }

    @Unique
    private DataComponentType<String> torrential$getComponent(String type) {
        return switch (type) {
            case "hook" -> ModComponents.HOOK_COMPONENT;
            case "line" -> ModComponents.LINE_COMPONENT;
            case "sinker" -> ModComponents.SINKER_COMPONENT;
            default -> throw new IllegalArgumentException("Unknown component type: " + type);
        };
    }
}
