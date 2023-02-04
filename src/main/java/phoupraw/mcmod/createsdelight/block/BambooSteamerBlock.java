package phoupraw.mcmod.createsdelight.block;

import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.BambooSteamerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.net.FileNameMap;
public class BambooSteamerBlock extends Block implements ITE<BambooSteamerBlockEntity> {
    public static final VoxelShape SHAPE = VoxelShapes.union(
      createCuboidShape(0, 1, 0, 16, 16, 16),
      createCuboidShape(0, 0, 0, 2, 1, 2),
      createCuboidShape(0, 0, 14, 2, 1, 16),
      createCuboidShape(14, 0, 0, 16, 1, 2),
      createCuboidShape(14, 0, 14, 16, 1, 16));

    public BambooSteamerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<BambooSteamerBlockEntity> getTileEntityClass() {
        return BambooSteamerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BambooSteamerBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.BAMBOO_STEAMER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (world.getBlockEntity(pos, MyBlockEntityTypes.BAMBOO_STEAMER).orElseThrow().isWorkable()) {
            world.addParticle(ParticleTypesRegistry.STEAM.get(), pos.getX() + 0.2 + random.nextDouble() * 0.6, pos.getY()-0.5 + random.nextDouble(), pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0, 0);
        }
    }
}
