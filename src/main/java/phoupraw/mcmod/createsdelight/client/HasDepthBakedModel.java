package phoupraw.mcmod.createsdelight.client;

import net.fabricmc.fabric.api.renderer.v1.model.ModelHelper;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;

public interface HasDepthBakedModel extends BakedModel {
    @Override
    default boolean useAmbientOcclusion() {
        return true;
    }
    @Override
    default boolean hasDepth() {
        return true;
    }
    @Override
    default boolean isSideLit() {
        return true;
    }
    @Override
    default ModelTransformation getTransformation() {
        return ModelHelper.MODEL_TRANSFORM_BLOCK;
    }
}
