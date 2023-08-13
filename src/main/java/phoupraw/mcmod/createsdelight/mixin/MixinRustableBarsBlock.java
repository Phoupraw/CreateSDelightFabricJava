package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import phoupraw.mcmod.createsdelight.block.IronBarsBlock;
@Mixin(targets = "com.ordana.immersive_weathering.blocks.rustable.RustableBarsBlock")
@Pseudo
public class MixinRustableBarsBlock extends PaneBlock implements IWrenchable {
    public MixinRustableBarsBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var result = IronBarsBlock.onUse(this, state, world, pos, player, hand, hit);
        if (result != ActionResult.PASS) return result;
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return IronBarsBlock.getRotatedBlockState(this, originalState, targetedFace);
    }
}
