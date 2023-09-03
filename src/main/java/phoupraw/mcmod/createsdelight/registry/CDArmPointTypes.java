package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.kinetics.mechanicalArm.ArmInteractionPointType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public final class CDArmPointTypes {

    private CDArmPointTypes() {
    }

    /**
     * 只要{@link #canCreatePoint(World, BlockPos, BlockState)}的{@code state}符合{@link #getBlock()}，就可以创建，创建的是{@link ArmInteractionPoint}
     * @see CDArmPointTypes
     */
    public static class DefaultPointType extends ArmInteractionPointType {

        private final Block block;

        /**
         * @param id {@link ArmInteractionPointType#ArmInteractionPointType(Identifier)}
         * @param block {@link #getBlock()}
         */
        public DefaultPointType(Identifier id, Block block) {
            super(id);
            this.block = block;
        }

        @Override
        public boolean canCreatePoint(World level, BlockPos pos, BlockState state) {
            return state.isOf(getBlock());
        }

        @Override
        public @NotNull ArmInteractionPoint createPoint(World level, BlockPos pos, BlockState state) {
            return new ArmInteractionPoint(this, level, pos, state);
        }

        public Block getBlock() {
            return block;
        }

    }

}
