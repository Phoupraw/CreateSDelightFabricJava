package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.Map;
@Deprecated
public class BlockVoxel {
    public final Vec3i size;
    public final Map<BlockPos, BlockState> blocks;
    public BlockVoxel(Vec3i size, Map<BlockPos, BlockState> blocks) {
        this.size = size;
        this.blocks = blocks;
    }
}
