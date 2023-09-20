package phoupraw.mcmod.createsdelight.client;

import com.google.common.collect.ListMultimap;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.misc.ConstDefaultedMap;
import phoupraw.mcmod.createsdelight.misc.DefaultedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleBlockBakedModel implements HasDepthBakedModel {
    public static SimpleBlockBakedModel of(ListMultimap<@Nullable Direction, BakedQuad> cullFace2quads0, @NotNull Sprite particleSprite) {
        DefaultedMap<@Nullable Direction, List<BakedQuad>> cullFace2quads = new ConstDefaultedMap<>(new HashMap<>(MadeVoxelModel.DIRECTIONS_NULL.size()), List.of());
        for (Direction face : cullFace2quads0.asMap().keySet()) {
            cullFace2quads.put(face, cullFace2quads0.get(face));
        }
        return new SimpleBlockBakedModel(cullFace2quads, particleSprite);
    }
    public final Map<@Nullable Direction, List<BakedQuad>> cullFace2quads;
    private final Sprite particleSprite;
    public SimpleBlockBakedModel(Map<@Nullable Direction, List<BakedQuad>> cullFace2quads, Sprite particleSprite) {
        for (Direction face : MadeVoxelModel.DIRECTIONS_NULL) {
            if (cullFace2quads.get(face) == null) {
                throw new IllegalArgumentException("cullFace2quads[%s] == null".formatted(face == null ? null : face.asString()));
            }
        }
        this.cullFace2quads = cullFace2quads;
        this.particleSprite = particleSprite;
    }
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return cullFace2quads.get(face);
    }
    @Override
    public boolean isBuiltin() {
        return true;
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
    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }
}
