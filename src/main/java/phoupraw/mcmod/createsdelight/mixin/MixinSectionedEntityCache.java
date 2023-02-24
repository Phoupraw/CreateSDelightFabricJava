package phoupraw.mcmod.createsdelight.mixin;

import it.unimi.dsi.fastutil.longs.LongSortedSet;
import net.minecraft.util.math.Box;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.entity.EntityTrackingSection;
import net.minecraft.world.entity.SectionedEntityCache;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.inject.InjectSectionedEntityCache;

import java.util.function.Consumer;
@Mixin(SectionedEntityCache.class)
public class MixinSectionedEntityCache {
    @Shadow
    @Final
    private LongSortedSet trackedPositions;

    @Inject(method = "forEachInBox", at = @At("HEAD"))
    private void printEntities(Box box, Consumer<EntityTrackingSection<EntityLike>> action, CallbackInfo ci) {
        InjectSectionedEntityCache.printEntities(box, action, trackedPositions);
    }
}
