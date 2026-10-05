package sircow.torrential.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.nautilus.Nautilus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import sircow.torrential.component.ModComponents;
import sircow.torrential.config.ConfigManager;
import sircow.torrential.config.ServerModConfig;
import sircow.torrential.tag.ModTags;
import sircow.torrential.trigger.ModTriggers;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@SuppressWarnings("FieldCanBeLocal")
@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Shadow @Mutable @Final private int lureSpeed;
    @Unique private boolean lureSpeedModified;
    @Shadow public abstract @Nullable Player getPlayerOwner();

    @Unique
    private ItemStack getRod(Player player) {
        ItemStack main = player.getMainHandItem();
        if (main.has(ModComponents.HOOK_COMPONENT)) return main;

        ItemStack off = player.getOffhandItem();
        if (off.has(ModComponents.HOOK_COMPONENT)) return off;

        return ItemStack.EMPTY;
    }

    // hook effect
    @Inject(method = "catchingFish", at = @At("HEAD"))
    private void torrential$addLureSpeed(BlockPos pos, CallbackInfo ci) {
        if (lureSpeedModified) return;

        Player owner = this.getPlayerOwner();
        if (owner == null) return;

        ItemStack rod = getRod(owner);
        if (rod.isEmpty()) return;

        ServerModConfig config = ConfigManager.getServer();
        MobEffectInstance conduit = owner.getEffect(MobEffects.CONDUIT_POWER);
        if (conduit != null) this.lureSpeed += (conduit.getAmplifier() + 1) * config.conduitLureBonus;
        MobEffectInstance blessing = owner.getEffect(MobEffects.BREATH_OF_THE_NAUTILUS);
        if (blessing != null) this.lureSpeed += config.breathLureBonus;

        String hook = rod.get(ModComponents.HOOK_COMPONENT);

        if (Objects.equals(hook, "copper")) this.lureSpeed += config.hookSpeedCopper;
        else if (Objects.equals(hook, "iron")) this.lureSpeed += config.hookSpeedIron;
        else if (Objects.equals(hook, "prismarine")) this.lureSpeed += config.hookSpeedPrismarine;
        else if (Objects.equals(hook, "diamond")) this.lureSpeed += config.hookSpeedDiamond;
        else if (Objects.equals(hook, "netherite")) this.lureSpeed += config.hookSpeedNetherite;

        lureSpeedModified = true;
    }

    // line effect
    @ModifyArg(method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;<init>(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V", ordinal = 0), index = 4)
    private ItemStack torrential$addFortune(ItemStack originalStack) {
        Player owner = this.getPlayerOwner();
        if (owner == null || originalStack.isEmpty()) return originalStack;

        ItemStack rod = getRod(owner);
        if (rod.isEmpty()) return originalStack;

        if (!(originalStack.is(ModTags.FISHING_LOOT_FISH) || originalStack.is(ModTags.FISHING_LOOT_VARIETY) || originalStack.is(ModTags.FISHING_LOOT_JUNK))) return originalStack;

        Random random = new Random();

        ServerModConfig config = ConfigManager.getServer();
        int bonus = 0;
        String line = rod.get(ModComponents.LINE_COMPONENT);
        double chance;

        if (Objects.equals(line, "copper")) {
            chance = 1.0 - (2.0 / (config.lineFortuneCopper + 2.0));
            if (random.nextDouble() < chance) bonus++;
        }
        else if (Objects.equals(line, "iron")) {
            chance = 1.0 - (2.0 / (config.lineFortuneIron + 2.0));
            if (random.nextDouble() < chance) bonus++;
        }
        else if (Objects.equals(line, "prismarine")) {
            chance = 1.0 - (2.0 / (config.lineFortunePrismarine + 2.0));
            if (random.nextDouble() < chance) bonus++;
            if (random.nextDouble() < chance) bonus++;
        }
        else if (Objects.equals(line, "diamond")) {
            chance = 1.0 - (2.0 / (config.lineFortuneDiamond + 2.0));
            if (random.nextDouble() < chance) bonus++;
            if (random.nextDouble() < chance) bonus++;
        }
        else if (Objects.equals(line, "netherite")) {
            chance = 1.0 - (2.0 / (config.lineFortuneNetherite + 2.0));
            if (random.nextDouble() < chance) bonus++;
            if (random.nextDouble() < chance) bonus++;
            if (random.nextDouble() < chance) bonus++;
        }

        if (bonus <= 0) return originalStack;

        ItemStack copy = originalStack.copy();
        copy.grow(bonus);
        return copy;
    }

    // sinker effect
    @ModifyArgs(method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/LootParams$Builder;withLuck(F)Lnet/minecraft/world/level/storage/loot/LootParams$Builder;"))
    private void torrential$addLuck(Args args) {
        float base = args.get(0);
        Player owner = this.getPlayerOwner();
        if (owner == null) return;

        ItemStack rod = getRod(owner);
        if (rod.isEmpty()) return;

        float result = base;

        ServerModConfig config = ConfigManager.getServer();
        MobEffectInstance conduit = owner.getEffect(MobEffects.CONDUIT_POWER);
        if (conduit != null) result += (conduit.getAmplifier() + 1) * (float) config.conduitLuckBonus;
        MobEffectInstance blessing = owner.getEffect(MobEffects.BREATH_OF_THE_NAUTILUS);
        if (blessing != null) result += (float) config.breathLuckBonus;

        String sinker = rod.get(ModComponents.SINKER_COMPONENT);

        if (Objects.equals(sinker, "copper")) result += (float) config.sinkerLuckCopper;
        else if (Objects.equals(sinker, "iron")) result += (float) config.sinkerLuckIron;
        else if (Objects.equals(sinker, "prismarine")) result += (float) config.sinkerLuckPrismarine;
        else if (Objects.equals(sinker, "diamond")) result += (float) config.sinkerLuckDiamond;
        else if (Objects.equals(sinker, "netherite")) result += (float) config.sinkerLuckNetherite;

        args.set(0, result);
    }

    // trigger fish treasure advancement
    @Inject(method = "retrieve", at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private void torrential$onEachFishedItem(ItemStack rod, CallbackInfoReturnable<Integer> cir, @Local(name = "items") List<ItemStack> items) {
        Player owner = this.getPlayerOwner();
        if (!(owner instanceof ServerPlayer serverPlayer)) return;

        for (ItemStack itemStack : items) {
            if (itemStack.is(ModTags.FISHING_LOOT_FISH) && serverPlayer.getVehicle() instanceof Nautilus) ModTriggers.FISH_ON_NAUTILUS.trigger(serverPlayer);
        }
    }

    @Inject(method = "retrieve", at = @At(value = "TAIL"))
    private void torrential$causeExhaustion(ItemStack rod, CallbackInfoReturnable<Integer> cir) {
        Player owner = this.getPlayerOwner();
        if (owner != null) owner.causeFoodExhaustion(0.2F);
    }
}
