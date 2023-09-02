package phoupraw.mcmod.createsdelight;

import com.google.common.collect.ListMultimap;
import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class SimpleBakedBlockModel implements BakedModel {

    private final ListMultimap<@Nullable Direction, BakedQuad> faces2quads;
    private final Sprite particleSprite;

    public SimpleBakedBlockModel(ListMultimap<@Nullable Direction, BakedQuad> faces2quads, @NotNull Sprite particleSprite) {
        this.faces2quads = faces2quads;
        this.particleSprite = particleSprite;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return getFaces2quads().get(face);
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
        return particleSprite;
    }

    @Override
    public ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    public ListMultimap<Direction, BakedQuad> getFaces2quads() {
        return faces2quads;
    }

    @Override
    public String toString() {
        return "SimpleBakedModel{" +
               "faces2quads=" + faces2quads + ", " +
               "particle=" + particleSprite + '}';
    }


}
