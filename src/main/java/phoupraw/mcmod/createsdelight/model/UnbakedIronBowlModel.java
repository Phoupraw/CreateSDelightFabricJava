package phoupraw.mcmod.createsdelight.model;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.data.client.ModelIds;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
public class UnbakedIronBowlModel implements UnbakedModel {

    public static final Identifier ID = ModelIds.getItemModelId(CDItems.IRON_BOWL);
    public static final Identifier BOWL = CDIdentifiers.of("item/iron_bowl_bowl");

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of(BOWL);
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return List.of();
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return new BakedIronBowlModel(Objects.requireNonNull(loader.getOrLoadModel(BOWL).bake(loader, textureGetter, rotationContainer, modelId)));
    }

}
