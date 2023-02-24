package phoupraw.mcmod.createsdelight.inject;

import it.unimi.dsi.fastutil.longs.LongSortedSet;
import net.minecraft.util.math.Box;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.entity.EntityTrackingSection;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.function.Consumer;
@ApiStatus.Internal
public class InjectSectionedEntityCache {
    public static final boolean DEBUG = false;

    public static void printEntities(Box box, Consumer<EntityTrackingSection<EntityLike>> action, LongSortedSet trackedPositions) {
        if (DEBUG) {
            CreateSDelight.LOGGER.debug("trackedPositions.size() = " + trackedPositions.size());
            CreateSDelight.LOGGER.info("trackedPositions.size() = " + trackedPositions.size());
        }
    }
}
