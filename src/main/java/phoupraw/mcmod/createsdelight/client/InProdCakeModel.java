package phoupraw.mcmod.createsdelight.client;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jozufozu.flywheel.util.AnimationTickHolder;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.data.client.ModelIds;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import phoupraw.mcmod.createsdelight.block.PrintedCakeBlock;
import phoupraw.mcmod.createsdelight.block.entity.InProdCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.List;
import java.util.function.Supplier;

public class InProdCakeModel implements BakedModel {
    public static final Identifier ID = ModelIds.getBlockModelId(CSDBlocks.IN_PROD_CAKE);
    public static final LoadingCache<VoxelCake, BakedModel> CACHE = CacheBuilder.newBuilder().build(CacheLoader.from(InProdCakeModel::toModel));
    public static BakedModel toModel(VoxelCake voxelCake) {
        return PrintedCakeModel.content2model(voxelCake, PrintedCakeBlock.defaultFacing());
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }
    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }
    @Override
    public boolean hasDepth() {
        return true;
    }
    @Override
    public boolean isSideLit() {
        return true;
    }
    @Override
    public boolean isBuiltin() {
        return false;
    }
    @Override
    public Sprite getParticleSprite() {
        //noinspection ConstantConditions
        return FluidVariantRendering.getSprite(FluidVariant.of(Milk.STILL_MILK));
    }
    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }
    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
    @Override
    public boolean isVanillaAdapter() {
        return false;
    }
    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (!(blockView.getBlockEntity(pos) instanceof InProdCakeBlockEntity inProd)) return;
        VoxelCake voxelCake = inProd.getVoxelCake();
        if (voxelCake == null) return;
        BakedModel bakedModel = CACHE.getUnchecked(voxelCake);
        BlockPos relative = inProd.relative;
        boolean shrink = relative != null && inProd.direction == null && false;
        if (shrink) {
            int edgeLen = inProd.edgeLen;
            Vector3f center = Vec3d.of(relative).toVector3f().mul(-1f / edgeLen);
            context.pushTransform(quad -> {
                Vector3f pos1 = new Vector3f();
                for (int i = 0; i < 4; i++) {
                    quad.copyPos(i, pos1);
                    pos1.add(center);
                    pos1.mul(1 + (edgeLen - 1) * (inProd.getProgress() - InProdCakeBlockEntity.SHRINKING_STEPS * AnimationTickHolder.getPartialTicks()));
                    quad.pos(i, pos1);
                }
                return true;
            });
        }
        bakedModel.emitBlockQuads(blockView, state, pos, randomSupplier, context);
        if (shrink) {
            context.popTransform();
        }
    }
}
