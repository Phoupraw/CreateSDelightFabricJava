package phoupraw.mcmod.createsdelight.mixin.client;

import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(JsonUnbakedModel.class)
class MJsonUnbakedModel {
    /** 当前模型 */
    @Unique private static final ThreadLocal<JsonUnbakedModel> LOCAL_setParents_jsonUnbakedModel = new ThreadLocal<>();
    /** 父模型 */
    @Unique private static final ThreadLocal<UnbakedModel> LOCAL_setParents_unbakedModel = new ThreadLocal<>();
    //    @ModifyVariable(method = "setParents", at = @At(value = "INVOKE", target = "Ljava/util/Set;add(Ljava/lang/Object;)Z", remap = false), name = "jsonUnbakedModel")
    //    private JsonUnbakedModel captureLocal_setParents_jsonUnbakedModel(JsonUnbakedModel jsonUnbakedModel) {
    //        LOCAL_setParents_jsonUnbakedModel.set(jsonUnbakedModel);
    //        return jsonUnbakedModel;
    //    }
    //    @SuppressWarnings("MixinAnnotationTarget")
    //    @WrapOperation(method = "setParents", constant = @Constant(classValue = JsonUnbakedModel.class, log = true, ordinal = 0))
    //    private boolean captureLocal_setParents_unbakedModel(Object unbakedModel, Operation<Boolean> original) {
    //        LOCAL_setParents_unbakedModel.set((UnbakedModel) unbakedModel);
    //        return original.call(unbakedModel);
    //    }
    //@ModifyArg(method = "setParents", at = @At(value = "INVOKE", target = "Ljava/lang/IllegalStateException;<init>(Ljava/lang/String;)V", remap = false))
    //private String detailMessege(String stupidDetailless) {
    //    return "BlockModel(%s) parent(%s) has to be a block model.".formatted(LOCAL_setParents_jsonUnbakedModel.get(), LOCAL_setParents_unbakedModel.get());
    //}
}
