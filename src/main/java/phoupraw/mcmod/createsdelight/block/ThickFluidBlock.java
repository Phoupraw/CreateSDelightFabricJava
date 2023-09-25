package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Optional;

public class ThickFluidBlock extends Block implements FluidDrainable {
    /**
     @see PowderSnowBlock#FALLING_SHAPE
     */
    public static final VoxelShape FALLING_SHAPE = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, 0.9F, 1.0);
    public ThickFluidBlock(Settings settings) {
        super(settings);
    }
    /**
     @param state
     @param world
     @param pos
     @param entity
     @see PowderSnowBlock#onEntityCollision(BlockState, World, BlockPos, Entity)
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.9F, 1.5, 0.9F));
        }
    }
    /**
     @param world
     @param state
     @param pos
     @param entity
     @param fallDistance
     @see PowderSnowBlock#onLandedUpon(World, BlockState, BlockPos, Entity, float)
     */
    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!((double) fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.FallSounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = (double) fallDistance < 7.0 ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundEvent, 1.0F, 1.0F);
        }
    }
    /**
     @param state
     @param world
     @param pos
     @param context
     @return
     @see PowderSnowBlock#getCollisionShape
     */
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5F) {
                    return FALLING_SHAPE;
                }
                if (entity instanceof FallingBlockEntity) {
                    return super.getCollisionShape(state, world, pos, context);
                }
            }
        }
        return VoxelShapes.empty();
    }
    /**
     @param state
     @param world
     @param pos
     @param context
     @return
     @see PowderSnowBlock#getCameraCollisionShape
     */
    @Override
    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
    /**
     @param state
     @param stateFrom
     @param direction
     @return
     @see PowderSnowBlock#isSideInvisible
     */
    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        return /*stateFrom.isOf(this) || */super.isSideInvisible(state, stateFrom, direction);
    }
    /**
     @param state
     @param world
     @param pos
     @return
     @see PowderSnowBlock#getCullingShape
     */
    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }
    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        return asItem().getDefaultStack();
    }
    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
    }
}
