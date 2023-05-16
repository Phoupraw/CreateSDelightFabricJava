package phoupraw.mcmod.createsdelight.model;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
public class PrintedCakeModel implements BakedModel, FabricBakedModel {
    public static final Identifier BLOCK_ID = ModelIds.getBlockModelId(CDBlocks.PRINTED_CAKE);
    public static final Identifier ITEM_ID = ModelIds.getItemModelId(CDItems.PRINTED_CAKE);
    public static final Map<UUID, List<BakedQuad>> CACHE = new HashMap<>();
    public final ThreadLocal<NbtCompound> theRendering = new ThreadLocal<>();

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {

    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {
        var nbt = stack.getNbt();
        if (nbt == null) return;

        theRendering.set(stack.getNbt());
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        var nbt = theRendering.get();
        if (nbt != null) {
            var uuid = nbt.getUuid("cache");
            var quads = CACHE.get(uuid);
            if (quads == null) {
                quads = new ArrayList<>();

            }
        }
        return List.of();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return null;
    }

    @Override
    public ModelTransformation getTransformation() {
        return null;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return null;
    }

    public static class Unbaked implements UnbakedModel {

        @Override
        public Collection<Identifier> getModelDependencies() {
            return List.of();
        }

        @Override
        public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
            return List.of();
        }

        @Nullable
        @Override
        public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
            return new PrintedCakeModel();
        }
    }
}
