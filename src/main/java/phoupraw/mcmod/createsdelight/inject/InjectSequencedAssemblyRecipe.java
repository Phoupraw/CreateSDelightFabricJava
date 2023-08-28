package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipe;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
@ApiStatus.Internal
public final class InjectSequencedAssemblyRecipe {
    public interface Interface {
        static List<ProcessingOutput> getResultPool(SequencedAssemblyRecipe self) {
            return ((Interface) self).getResultPool();
        }
        List<ProcessingOutput> getResultPool();
    }

    private InjectSequencedAssemblyRecipe() {

    }
}
