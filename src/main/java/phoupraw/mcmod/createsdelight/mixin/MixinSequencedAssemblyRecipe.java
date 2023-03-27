package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import phoupraw.mcmod.createsdelight.inject.InjectSequencedAssemblyRecipe;

import java.util.List;
@Mixin(SequencedAssemblyRecipe.class)
public abstract class MixinSequencedAssemblyRecipe implements InjectSequencedAssemblyRecipe.Interface {
    @Shadow(remap = false)
    protected List<ProcessingOutput> resultPool;

    @Override
    public List<ProcessingOutput> getResultPool() {
        return resultPool;
    }
}
