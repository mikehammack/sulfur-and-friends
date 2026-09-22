package com.maximarcana.sulfurfriends.mixin;

import com.maximarcana.sulfurfriends.geyser.GeyserWarning;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hooks the potent sulfur block entity's server countdown ticker
 * (the once-per-second lambda that decrements {@code waitingCountdown})
 * to emit an early eruption warning.
 */
@Mixin(PotentSulfurBlockEntity.class)
public class PotentSulfurGeyserWarningMixin {
    @Inject(
        method = "lambda$static$4(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/entity/PotentSulfurBlockEntity;)V",
        at = @At("HEAD")
    )
    private static void sulfurandfriends$warnBeforeEruption(
        Level level, BlockPos pos, BlockState state, PotentSulfurBlockEntity blockEntity, CallbackInfo ci
    ) {
        GeyserWarning.checkAndWarn(level, pos, blockEntity);
    }
}
