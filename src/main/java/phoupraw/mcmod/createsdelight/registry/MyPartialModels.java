package phoupraw.mcmod.createsdelight.registry;

import com.jozufozu.flywheel.core.PartialModel;
import net.minecraft.data.client.ModelIds;
public final class MyPartialModels {

    public static final PartialModel SPRINKLER_LID = new PartialModel(ModelIds.getBlockSubModelId(MyBlocks.SPRINKLER, "_lid"));
    public static final PartialModel VERTICAL_CUTTER_KNIFE = new PartialModel(ModelIds.getBlockSubModelId(MyBlocks.VERTICAL_CUTTER, "_knife"));
    public static final PartialModel PRESSURE_COOKER_LID = new PartialModel(ModelIds.getBlockSubModelId(MyBlocks.PRESSURE_COOKER, "_lid"));
    public static final PartialModel MINCER_LID = new PartialModel(ModelIds.getBlockSubModelId(MyBlocks.MINCER, "_lid"));
    public static final PartialModel MINCER_WHISK = new PartialModel(ModelIds.getBlockSubModelId(MyBlocks.MINCER, "_whisk"));

    private MyPartialModels() {}
}
