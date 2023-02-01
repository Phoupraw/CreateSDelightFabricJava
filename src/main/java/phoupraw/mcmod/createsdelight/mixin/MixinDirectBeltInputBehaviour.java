package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import phoupraw.mcmod.createsdelight.api.DirectBeltInput;
@Mixin(DirectBeltInputBehaviour.class)
public abstract class MixinDirectBeltInputBehaviour implements DirectBeltInput.InsertionHandler {

}
