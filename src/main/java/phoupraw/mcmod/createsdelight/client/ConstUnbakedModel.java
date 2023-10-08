package phoupraw.mcmod.createsdelight.client;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelBaker;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.resource.Material;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class ConstUnbakedModel implements UnbakedModel {

    public final @Nullable BakedModel bakedModel;

    public ConstUnbakedModel(@Nullable BakedModel bakedModel) {this.bakedModel = bakedModel;}

    @Override
    public Collection<Identifier> getModelDependencies() {
        return List.of();
    }
    @Override
    public void resolveParents(Function<Identifier, UnbakedModel> modelLoader) {

    }
    @Nullable
    @Override
    public BakedModel bake(ModelBaker baker, Function<Material, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
        return bakedModel;
    }

}
