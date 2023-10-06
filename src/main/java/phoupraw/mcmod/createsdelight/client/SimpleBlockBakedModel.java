package phoupraw.mcmod.createsdelight.client;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.texture.Sprite;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.List;

public class SimpleBlockBakedModel implements HasDepthBakedModel {
    public static final Sprite EMPTY_SPRITE = MinecraftClient.getInstance().getSpriteAtlas(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).apply(CSDIdentifiers.EMPTY);
    public static final SimpleBlockBakedModel EMPTY = new SimpleBlockBakedModel(MultimapBuilder.hashKeys().linkedListValues().build(), EMPTY_SPRITE);
    public final ListMultimap<@Nullable Direction, BakedQuad> cullFace2quads;
    private final Sprite particleSprite;
    public SimpleBlockBakedModel(ListMultimap<@Nullable Direction, BakedQuad> cullFace2quads, Sprite particleSprite) {
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
