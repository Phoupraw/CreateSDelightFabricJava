package phoupraw.mcmod.createsdelight.model;

import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import phoupraw.mcmod.createsdelight.registry.CDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.storage.IronBowlFluidStorage;
import phoupraw.mcmod.createsdelight.storage.IronBowlItemStorage;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
@SuppressWarnings("ClassCanBeRecord")
public class IronBowlModel implements BakedModel, FabricBakedModel {
    public static final RenderContext.QuadTransform TRANSFORM_BLOCK = IronBowlModel::transformBlock;
    public static final RenderContext.QuadTransform TRANSFORM_ITEM = IronBowlModel::transformItem;

    @SuppressWarnings("ConstantConditions")
    @Contract(pure = true)
    public static @NotNull MeshBuilder getMeshBuilder() {
        return RendererAccess.INSTANCE.getRenderer().meshBuilder();
    }

    @Contract(mutates = "param", value = "_->true")
    public static boolean transformBlock(MutableQuadView quad) {
        Vector3f pos = new Vector3f();
        for (int i = 0; i < 4; i++) {
            quad.copyPos(i, pos);
            pos.mul(14 / 16f);
            pos.add(1 / 16f, 1 / 16f, 1 / 16f);
            quad.pos(i, pos);
        }
        return true;
    }

    @Contract(mutates = "param", value = "_->true")
    public static boolean transformItem(MutableQuadView quad) {
        Vector3f pos = new Vector3f();
        for (int i = 0; i < 4; i++) {
            quad.copyPos(i, pos);
            pos.rotateAxis(45, 0, -1, 0);
            //pos.rotate(new Quaternionf(Direction.DOWN.getUnitVector(), 45f, true));
            pos.add(0, 0, -0.70710678118654752440084436210485f);
            pos.mul(14 / 12f);
            pos.add(0.5f, 1 / 16f, 0.5f);
            quad.pos(i, pos);
        }
        return true;
    }

    public static void appendQuads(List<BakedQuad> quads, @NotNull ItemStack itemStack, @Nullable BlockState state, @Nullable Direction face, Random random) {
        ContainerItemContext itemContext = ContainerItemContext.withConstant(itemStack);
        var fluidS = new IronBowlFluidStorage(itemContext, null);
        if (!fluidS.isResourceBlank()) {
            var builder = getMeshBuilder();
            QuadEmitter emitter = builder.getEmitter();
            Sprite sprite = FluidVariantRendering.getSprite(fluidS.getResource());
            int color = FluidVariantRendering.getColor(fluidS.getResource());
            emitter.square(Direction.UP, 1 / 16f, 1 / 16f, 15 / 16f, 15 / 16f, 6.5f / 16f);
            emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.spriteColor(0, color, color, color, color);
            emitter.emit();
            builder.build().forEach(quadView -> quads.add(quadView.toBakedQuad(0, sprite, false)));
        } else {
            var itemS = new IronBowlItemStorage(itemContext, null);
            if (!itemS.isResourceBlank()) {
                var itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                ItemStack nestedStack = itemS.getResource().toStack();
                var itemModel = (BakedModel & FabricBakedModel) itemRenderer.getModel(nestedStack, null, null, 0);
                if (itemModel.hasDepth()) {
                    var builder = getMeshBuilder();
                    QuadEmitter emitter = builder.getEmitter();
                    for (BakedQuad quad : itemModel.getQuads(state, face, random)) {

                    }
                } else {
                }
            }
        }
    }

    public final ThreadLocal<ItemStack> theRendering = new ThreadLocal<>();
    private final BakedModel bowlModel;

    public IronBowlModel(BakedModel bowlModel) {
        this.bowlModel = bowlModel;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {

    }

    @Override
    public void emitItemQuads(ItemStack itemStack, Supplier<Random> randomSupplier, RenderContext context) {
        theRendering.set(itemStack);
        if (!Screen.hasControlDown()) {
            context.bakedModelConsumer().accept(getBowlModel());
        }
        ContainerItemContext itemContext = ContainerItemContext.withConstant(itemStack);
        var fluidS = new IronBowlFluidStorage(itemContext, null);
        if (!fluidS.isResourceBlank()) {
            var builder = getMeshBuilder();
            QuadEmitter emitter = builder.getEmitter();
            Sprite sprite = FluidVariantRendering.getSprite(fluidS.getResource());
            int color = FluidVariantRendering.getColor(fluidS.getResource());
            emitter.square(Direction.UP, 1 / 16f, 1 / 16f, 15 / 16f, 15 / 16f, 6.5f / 16f);
            emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.spriteColor(0, color, color, color, color);
            emitter.emit();
//            context.pushTransform(quad -> {
////                Vec3f pos = new Vec3f();
////                for (int i = 0; i < 4; i++) {
////                    quad.copyPos(i, pos);
////                    pos.rotate(new Quaternion(Direction.DOWN.getUnitVector(), 45f, true));
////                    pos.add(0, 0, -0.70710678118654752440084436210485f);
////                    pos.scale(14 / 12f);
////                    pos.add(0.5f, 1 / 16f, 0.5f);
////                    quad.pos(i, pos);
////                }
//                return true;
//            });
            context.meshConsumer().accept(builder.build());
//            context.popTransform();
        } else {
            var itemS = new IronBowlItemStorage(itemContext, null);
            if (!itemS.isResourceBlank()) {
                var itemRenderer = MinecraftClient.getInstance().getItemRenderer();
                ItemStack nestedStack = itemS.getResource().toStack();
                var itemModel = (BakedModel & FabricBakedModel) itemRenderer.getModel(nestedStack, null, null, 0);
                if (itemModel.hasDepth()) {
                    context.pushTransform(TRANSFORM_BLOCK);
                } else {
                    context.pushTransform(TRANSFORM_ITEM);
                }
                context.bakedModelConsumer().accept(itemModel);
                if (!itemModel.isVanillaAdapter()) {
                    itemModel.emitItemQuads(nestedStack, randomSupplier, context);
                }
                context.popTransform();
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        //        CreateSDelight.LOGGER.info("phoupraw.mcmod.createsdelight.model.BakedIronBowlModel.getQuads "+ AnimationTickHolder.getPartialTicks());
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
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(ModelIds.getBlockModelId(AllBlocks.BASIN.get()));
    }

    @Override
    public @Nullable ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    public BakedModel getBowlModel() {
        return bowlModel;
    }

    public static class Unbaked implements UnbakedModel {

        public static final Identifier ID = ModelIds.getItemModelId(CDItems.IRON_BOWL);
        public static final Identifier BOWL = CDIdentifiers.of("item/iron_bowl_bowl");

        @Override
        public Collection<Identifier> getModelDependencies() {
            return List.of(BOWL);
        }

        @Override
        public void setParents(Function<Identifier, UnbakedModel> modelLoader) {

        }

        @Nullable
        @Override
        public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
            return new IronBowlModel(baker.getOrLoadModel(BOWL).bake(baker, textureGetter, rotationContainer, modelId));
        }

    }
}
