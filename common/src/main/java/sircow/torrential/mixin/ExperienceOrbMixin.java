package sircow.torrential.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import sircow.torrential.component.ModComponents;

import java.util.List;

@Mixin(ExperienceOrb.class)
public class ExperienceOrbMixin {
    @ModifyExpressionValue(method = "playerTouch", at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/ExperienceOrb;value:I", opcode = Opcodes.GETFIELD))
    private int torrential$repairRodPartsBeforeVanillaMending(int originalValue, @Local(argsOnly = true) Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return originalValue;

        int consumed = torrential$repairRodParts(serverPlayer, originalValue);

        return originalValue - consumed;
    }

    @Unique
    private static int torrential$repairRodParts(ServerPlayer player, int xp) {
        if (xp <= 0) return 0;

        ItemStack rod = player.getMainHandItem();

        if (!rod.is(Items.FISHING_ROD) || !rod.has(ModComponents.HOOK_COMPONENT)) {
            rod = player.getOffhandItem();
            if (!rod.is(Items.FISHING_ROD) || !rod.has(ModComponents.HOOK_COMPONENT)) return 0;
        }

        int hookDamage = rod.getOrDefault(ModComponents.HOOK_DURABILITY, 0);
        int lineDamage = rod.getOrDefault(ModComponents.LINE_DURABILITY, 0);
        int sinkerDamage = rod.getOrDefault(ModComponents.SINKER_DURABILITY, 0);

        boolean hookMending = rod.getOrDefault(ModComponents.HOOK_ENCHANTMENTS, List.of()).contains(ModComponents.MENDING_ID) && hookDamage > 0;
        boolean lineMending = rod.getOrDefault(ModComponents.LINE_ENCHANTMENTS, List.of()).contains(ModComponents.MENDING_ID) && lineDamage > 0;
        boolean sinkerMending = rod.getOrDefault(ModComponents.SINKER_ENCHANTMENTS, List.of()).contains(ModComponents.MENDING_ID) && sinkerDamage > 0;

        int damagedCount = (hookMending ? 1 : 0) + (lineMending ? 1 : 0) + (sinkerMending ? 1 : 0);
        if (damagedCount == 0) return 0;

        int share = xp / damagedCount;
        int remainder = xp % damagedCount;
        int totalConsumed = 0;
        int assigned = 0;

        if (hookMending) {
            int partXp = share + (assigned < remainder ? 1 : 0);

            assigned++;

            if (partXp > 0) {
                int repairAmount = partXp * 2;
                int toRepair = Math.min(repairAmount, hookDamage);

                rod.set(ModComponents.HOOK_DURABILITY, hookDamage - toRepair);
                totalConsumed += (toRepair * partXp) / repairAmount;
                hookDamage -= toRepair;
            }
        }
        if (lineMending) {
            int partXp = share + (assigned < remainder ? 1 : 0);

            if (partXp > 0) {
                int repairAmount = partXp * 2;
                int toRepair = Math.min(repairAmount, lineDamage);

                rod.set(ModComponents.LINE_DURABILITY, lineDamage - toRepair);
                totalConsumed += (toRepair * partXp) / repairAmount;
                lineDamage -= toRepair;
            }
        }
        if (sinkerMending) {
            int partXp = share;

            if (partXp > 0) {
                int repairAmount = partXp * 2;
                int toRepair = Math.min(repairAmount, sinkerDamage);

                rod.set(ModComponents.SINKER_DURABILITY, sinkerDamage - toRepair);
                totalConsumed += (toRepair * partXp) / repairAmount;
                sinkerDamage -= toRepair;
            }
        }

        int leftover = xp - totalConsumed;

        if (leftover > 0) {
            if (hookMending && hookDamage > 0) {
                int repairAmount = leftover * 2;
                int toRepair = Math.min(repairAmount, hookDamage);

                rod.set(ModComponents.HOOK_DURABILITY, hookDamage - toRepair);
                totalConsumed += (toRepair * leftover) / repairAmount;
                leftover -= (toRepair * leftover) / repairAmount;
            }
            if (leftover > 0 && lineMending && lineDamage > 0) {
                int repairAmount = leftover * 2;
                int toRepair = Math.min(repairAmount, lineDamage);

                rod.set(ModComponents.LINE_DURABILITY, lineDamage - toRepair);
                totalConsumed += (toRepair * leftover) / repairAmount;
                leftover -= (toRepair * leftover) / repairAmount;
            }
            if (leftover > 0 && sinkerMending && sinkerDamage > 0) {
                int repairAmount = leftover * 2;
                int toRepair = Math.min(repairAmount, sinkerDamage);

                rod.set(ModComponents.SINKER_DURABILITY, sinkerDamage - toRepair);
                totalConsumed += (toRepair * leftover) / repairAmount;
            }
        }
        return totalConsumed;
    }
}
