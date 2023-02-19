package phoupraw.mcmod.createsdelight.registry;

import com.jozufozu.flywheel.core.PartialModel;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public final class MyPartialModels {

    public static final PartialModel SPRINKLER_LID = new PartialModel(new Identifier(CreateSDelight.MOD_ID, "block/sprinkler_lid"));
    public static final PartialModel VERTICAL_CUTTER_KNIFE = new PartialModel(new Identifier(CreateSDelight.MOD_ID, "block/vertical_cutter_knife"));

    private MyPartialModels() {}
}
