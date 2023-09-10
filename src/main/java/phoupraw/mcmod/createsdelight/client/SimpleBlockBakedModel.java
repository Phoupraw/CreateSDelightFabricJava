package phoupraw.mcmod.createsdelight.client;

import com.google.common.collect.ListMultimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SimpleBlockBakedModel implements HasDepthBakedModel {
    private final ListMultimap<@Nullable Direction, BakedQuad> faces2quads;
    private final Sprite particleSprite;
    public SimpleBlockBakedModel(ListMultimap<@Nullable Direction, BakedQuad> faces2quads, @NotNull Sprite particleSprite) {
        this.faces2quads = faces2quads;
        this.particleSprite = particleSprite;
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return getFaces2quads().get(face);
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
    public String toString() {
        return "SimpleBakedModel{" +
               "faces2quads=" + faces2quads + ", " +
               "particle=" + particleSprite + '}';
    }

    public ListMultimap<Direction, BakedQuad> getFaces2quads() {
        return faces2quads;
    }

}
