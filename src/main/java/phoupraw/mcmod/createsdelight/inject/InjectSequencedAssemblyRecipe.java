package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
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
