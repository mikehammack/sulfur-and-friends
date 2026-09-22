package com.maximarcana.sulfurfriends.mixin;

import net.minecraft.world.entity.monster.cubemob.SulfurCube;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * A sulfur cube that has absorbed a block is a keeper, not ambient wildlife:
 * stop it from despawning while it carries a block.
 */
@Mixin(SulfurCube.class)
public class SulfurCubePersistenceMixin {
    @Inject(method = "requiresCustomPersistence", at = @At("HEAD"), cancellable = true)
    private void sulfurandfriends$persistWhenAbsorbed(CallbackInfoReturnable<Boolean> cir) {
        if (((SulfurCube) (Object) this).hasBodyItem()) {
            cir.setReturnValue(true);
        }
    }
}
