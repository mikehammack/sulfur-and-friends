package com.maximarcana.sulfurfriends.geyser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;

/**
 * Fires a short warning (particles + sound) a few seconds before a potent
 * sulfur geyser erupts. Called from a mixin into the geyser's server ticker.
 *
 * <p>The vanilla ticker runs once per second and decrements
 * {@code waitingCountdown} by 1 each run, erupting when it reaches 0. Warning
 * when the countdown reads {@link #WARNING_COUNTDOWN} at the start of a tick
 * therefore gives roughly {@code WARNING_COUNTDOWN - 1} seconds of lead time.
 * Every eruption cycle passes through that value exactly once, so no extra
 * bookkeeping is needed to avoid repeat warnings.
 */
public final class GeyserWarning {
    private static final int WARNING_COUNTDOWN = 4;

    private GeyserWarning() {
    }

    public static void checkAndWarn(Level level, BlockPos pos, PotentSulfurBlockEntity blockEntity) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        if (blockEntity.waitingCountdown != WARNING_COUNTDOWN) {
            return;
        }
        double x = pos.getX() + 0.5;
        double y = pos.getY() + 1.1;
        double z = pos.getZ() + 0.5;
        // Gas building up: sulfur bubbles rising out of the vent.
        serverLevel.sendParticles(ParticleTypes.SULFUR_BUBBLES, x, y, z, 16, 0.35, 0.7, 0.35, 0.06);
        // Low rumble swell using the eruption's own start sound, pitched down.
        serverLevel.playSound(null, x, y, z, SoundEvents.GEYSER_ERUPTION_START, SoundSource.BLOCKS, 1.0F, 0.6F);
    }
}
