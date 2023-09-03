package phoupraw.mcmod.createsdelight.client;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.data.client.ModelIds;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.ReadyCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.List;
import java.util.function.Supplier;

public class ReadyCakeModel implements BakedModel {

    public static final Identifier ID = ModelIds.getBlockModelId(CSDBlocks.READY_CAKE);
    public static final LoadingCache<CakeIngredient, BakedModel> CACHE = CacheBuilder.newBuilder().build(CacheLoader.from(ReadyCakeModel::makeModel));

    public static BakedModel makeModel(CakeIngredient cakeIngredient) {
        Sprite sprite = PrintedCakeModel.getSprite(cakeIngredient);
        @SuppressWarnings("ConstantConditions") MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter emitter = meshBuilder.getEmitter();
        ListMultimap<Direction, BakedQuad> faces2quads = MultimapBuilder.hashKeys().linkedListValues().build();
        for (Direction nominalFace : Direction.values()) {
            BakedQuad quad = emitter
              .square(nominalFace, 1, 1, 1, 1, 0)
              .spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV)
              .color(0xffffffff, 0xffffffff, 0xffffffff, 0xffffffff)
              .toBakedQuad(sprite);
            faces2quads.put(nominalFace, quad);
        }
        return new SimpleBakedBlockModel(faces2quads, sprite);
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
        return MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModelParticleSprite(Blocks.AIR.getDefaultState());
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
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        if (!(blockView.getBlockEntity(pos) instanceof ReadyCakeBlockEntity ready)) return;
        CakeIngredient cakeIngredient = ready.cakeIngredient;
        if (cakeIngredient == null) return;
        CACHE.getUnchecked(cakeIngredient).emitBlockQuads(blockView, state, pos, randomSupplier, context);
    }

}
