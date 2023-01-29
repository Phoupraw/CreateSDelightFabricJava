package phoupraw.mcmod.createsdelight;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
public class DefaultArmInteractionPointType extends ArmInteractionPointType {
    private final Block block;

    public DefaultArmInteractionPointType(Identifier id, Block block) {
        super(id);
        this.block = block;
    }

    @Override
    public boolean canCreatePoint(World level, BlockPos pos, BlockState state) {
        return state.isOf(block);
    }

    @Nullable
    @Override
    public ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
        return new ArmInteractionPoint(this, level, pos, state);
    }
}
