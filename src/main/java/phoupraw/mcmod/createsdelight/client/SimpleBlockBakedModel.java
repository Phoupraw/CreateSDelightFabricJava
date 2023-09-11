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
import java.util.Map;

public class SimpleBlockBakedModel implements HasDepthBakedModel {
    public static SimpleBlockBakedModel of(ListMultimap<@Nullable Direction, BakedQuad> cullFace2quads, @NotNull Sprite particleSprite) {
        return new SimpleBlockBakedModel((Map<@Nullable Direction, List<BakedQuad>>) (Object) cullFace2quads.asMap(), particleSprite);
    }
    public final Map<@Nullable Direction, List<BakedQuad>> cullFace2quads;
    private final Sprite particleSprite;
    public SimpleBlockBakedModel(Map<@Nullable Direction, List<BakedQuad>> cullFace2quads, Sprite particleSprite) {
        this.cullFace2quads = cullFace2quads;
        this.particleSprite = particleSprite;
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return cullFace2quads.get(face);
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
               "faces2quads=" + cullFace2quads + ", " +
               "particle=" + particleSprite + '}';
    }
}
