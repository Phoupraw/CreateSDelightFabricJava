package phoupraw.mcmod.createsdelight.client;

import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public interface CustomBlockModel extends HasDepthBakedModel {
    @Override
    default List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }
    @Override
    default boolean isBuiltin() {
        return true;
    }
    @Override
    default ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
    @Override
    default boolean isVanillaAdapter() {
        return false;
    }
    @Override
    void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context);
}
