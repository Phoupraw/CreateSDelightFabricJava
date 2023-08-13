package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import phoupraw.mcmod.createsdelight.api.DirectBeltInput;
@Mixin(value = DirectBeltInputBehaviour.class)
public abstract class MixinDirectBeltInputBehaviour implements DirectBeltInput.InsertionHandler {

}
