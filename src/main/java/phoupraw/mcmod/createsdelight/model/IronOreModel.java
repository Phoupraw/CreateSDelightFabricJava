package phoupraw.mcmod.createsdelight.model;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
@ApiStatus.Experimental
public class IronOreModel {

    public static final Map<Block, SpriteIdentifier> TEXTURES = new HashMap<>();
    public static final Map<Block, Sprite> SPRITES = new HashMap<>();

    public static class Unbaked implements UnbakedModel {

        @Override
        public Collection<Identifier> getModelDependencies() {
            return List.of();
        }

        @Override
        public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
            return TEXTURES.values();
        }

        @Nullable
        @Override
        public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {

            return null;
        }

    }

}
