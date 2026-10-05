package sircow.torrential.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sircow.torrential.config.ConfigManager;
import sircow.torrential.config.ServerModConfig;
import sircow.torrential.damage.ModDamageTypes;
import sircow.torrential.damage.NoLootingPlayerWrapper;
import sircow.torrential.trigger.ModTriggers;

import java.util.List;

@Mixin(ConduitBlockEntity.class)
public class ConduitBlockEntityMixin {
    @Unique private boolean wasActive;
    @Unique private boolean wasFullyPowered;

    // extend conduit radius
    @ModifyConstant(method = "getDestroyRangeAABB", constant = @Constant(doubleValue = 8.0F))
    private static double torrential$modifyDoubleValueAgain(double original) {
        return ConfigManager.getServer().conduitDamageRadius;
    }
    // damage speed
    @ModifyConstant(method = "clientTick", constant = @Constant(longValue = 40L))
    private static long torrential$modifyLongValue(long original) {
        return 1L;
    }
    @ModifyConstant(method = "serverTick", constant = @Constant(longValue = 40L))
    private static long torrential$modifyLongValueAgain(long original) {
        return 1L;
    }

    // remove in rain or water to grant effect
    @Inject(method = "applyEffects", at = @At("HEAD"), cancellable = true)
    private static void torrential$givePlayersEffects(Level level, BlockPos worldPosition, List<BlockPos> effectBlocks, CallbackInfo ci) {
        int structureSize = effectBlocks.size();
        int amplifier;

        if (structureSize >= 42) amplifier = 2;
        else if (structureSize >= 28) amplifier = 1;
        else amplifier = 0;

        int radius = structureSize / 7 * ConfigManager.getServer().conduitEffectMultiplier;

        AABB box = new AABB(worldPosition).inflate(radius).expandTowards(0.0, level.getHeight(), 0.0);
        List<Player> players = level.getEntitiesOfClass(Player.class, box);

        for (Player player : players) {
            if (!worldPosition.closerThan(player.blockPosition(), radius)) continue;

            player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 100, amplifier, true, true));
        }
        ci.cancel();
    }

    // check conduit damage blacklist
    @Unique
    private static boolean isConduitDamageBlacklisted(EntityType<?> type) {
        List<String> blacklist = ConfigManager.getServer().conduitDamageBlacklist;
        if (blacklist.isEmpty()) return false;

        Identifier typeId = BuiltInRegistries.ENTITY_TYPE.getKey(type);

        for (String entry : blacklist) {
            if (entry.startsWith("#")) {
                if (type.builtInRegistryHolder().is(TagKey.create(Registries.ENTITY_TYPE, Identifier.parse(entry.substring(1))))) return true;
            }
            else if (Identifier.parse(entry).equals(typeId)) {
                return true;
            }
        }
        return false;
    }

    // change magic damage to custom damage type which makes player-killed loot drop
    @Inject(method = "updateAndAttackTarget", at = @At("HEAD"), cancellable = true)
    private static void torrential$attackMultipleTargets(ServerLevel level, BlockPos worldPosition, BlockState blockState, ConduitBlockEntity conduit, boolean isActive, CallbackInfo ci) {
        ServerModConfig config = ConfigManager.getServer();

        if (!config.enableConduitDamage) {
            ci.cancel();
            return;
        }

        if (!isActive) return;

        AABB range = new AABB(worldPosition).inflate(config.conduitDamageRadius);
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                range,
                entity -> entity instanceof Enemy && entity.isInWaterOrRain() && !isConduitDamageBlacklisted(entity.getType())
        );

        if (!targets.isEmpty()) {
            ServerPlayer fakePlayer = level.players().isEmpty() ? null : level.players().getFirst();
            Player conduitKiller = fakePlayer != null ? new NoLootingPlayerWrapper(fakePlayer) : null;
            DamageSource source = ModDamageTypes.of(level, ModDamageTypes.CONDUIT, conduitKiller);

            for (LivingEntity target : targets) {
                if (!target.isAlive()) continue;
                target.hurtServer(level, source, 4.0F);

                if (level.getGameTime() % 20L == 0L) {
                    level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.CONDUIT_ATTACK_TARGET, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
            }
        }
        ci.cancel();
    }

    // award conduit advancements
    @Inject(method = "serverTick", at = @At("TAIL"))
    private static void torrential$onActivation(Level level, BlockPos pos, BlockState state, ConduitBlockEntity conduit, CallbackInfo ci) {
        if (level.isClientSide()) return;

        ConduitBlockEntityMixin self = (ConduitBlockEntityMixin)(Object)conduit;
        if (self != null) {
            boolean wasActive = self.wasActive;
            boolean isActive = conduit.isActive();
            int structureSize = ((ConduitBlockEntityAccessor) conduit).getEffectBlocks().size();
            boolean isFullyPowered = structureSize >= 42;
            boolean wasFullyPowered = self.wasFullyPowered;

            // activating conduit
            if (!wasActive && isActive) {
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(16));
                for (Player player : nearbyPlayers) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModTriggers.CONDUIT_POWER.trigger(serverPlayer);
                    }
                }
            }

            // fully activated conduit
            if (!wasFullyPowered && isFullyPowered) {
                List<Player> nearbyPlayers = level.getEntitiesOfClass(Player.class, new AABB(pos).inflate(16));
                for (Player player : nearbyPlayers) {
                    if (player instanceof ServerPlayer serverPlayer) {
                        ModTriggers.CONDUIT_POWER_FULL.trigger(serverPlayer);
                    }
                }
            }

            self.wasActive = isActive;
            self.wasFullyPowered = isFullyPowered;
        }
    }
}
