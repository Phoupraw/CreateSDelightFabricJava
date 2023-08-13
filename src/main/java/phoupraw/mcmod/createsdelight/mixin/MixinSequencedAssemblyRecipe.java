package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
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
