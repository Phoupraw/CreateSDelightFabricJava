package phoupraw.mcmod.createsdelight.registry;

import com.jozufozu.flywheel.core.PartialModel;
import net.minecraft.data.client.ModelIds;
public final class CDPartialModels {

    public static final PartialModel SPRINKLER_LID = new PartialModel(ModelIds.getBlockSubModelId(CDBlocks.SPRINKLER, "_lid"));
    public static final PartialModel VERTICAL_CUTTER_KNIFE = new PartialModel(ModelIds.getBlockSubModelId(CDBlocks.VERTICAL_CUTTER, "_knife"));
    public static final PartialModel PRESSURE_COOKER_LID = new PartialModel(ModelIds.getBlockSubModelId(CDBlocks.PRESSURE_COOKER, "_lid"));
    public static final PartialModel MINCER_LID = new PartialModel(ModelIds.getBlockSubModelId(CDBlocks.MINCER, "_lid"));
    public static final PartialModel MINCER_PROPELLER = new PartialModel(ModelIds.getBlockSubModelId(CDBlocks.MINCER, "_propeller"));

    private CDPartialModels() {
    }
}
