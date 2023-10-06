package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.util.math.Position;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Position.class)
public interface MPosition /*extends DefaultVector3dc*//*FIXME Vector3dc实属逆天*//* */ {
    @Shadow
    double getX();
    @Shadow
    double getY();
    @Shadow
    double getZ();
    //@Override
    //default double x() {
    //    return getX();
    //}
    //@Override
    //default double y() {
    //    return getY();
    //}
    //@Override
    //default double z() {
    //    return getZ();
    //}
}
