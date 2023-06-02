package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.fluid.Fluid;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
/**
 一些快捷注册的静态方法
 @since 1.0.0 */
public final class Registries {

    /**
     @since 1.0.0
     */
    @Contract("_, _ -> param2")
    public static <T extends Entity> EntityType<T> register(Identifier id, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, id, entityType);
    }

    /**
     @since 1.0.0
     */
    @Contract("_, _ -> param2")
    public static <T extends Fluid> T register(Identifier id, T fluid) {
        return Registry.register(Registry.FLUID, id, fluid);
    }

    /**
     @since 1.0.0
     */
    @Contract("_, _ -> param2")
    public static <T extends EntityAttribute> T register(Identifier id, T attribute) {
        return Registry.register(Registry.ATTRIBUTE, id, attribute);
    }

    /**
     @since 1.0.0
     */
    @Contract("_, _ -> param2")
    public static <T extends Enchantment> T register(Identifier id, T enchantment) {
        return Registry.register(Registry.ENCHANTMENT, id, enchantment);
    }

    /**
     @since 1.0.0
     */
    @Contract("_, _ -> param2")
    public static <T extends RecipeSerializer<R>, R extends Recipe<?>> T register(Identifier id, T serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, id, serializer);
    }

    /**
     @since 1.0.0
     */
    @Contract(value = "_,_->new", pure = true)
    public static <T extends BlockEntity> BlockEntityType<T> of(FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return FabricBlockEntityTypeBuilder.create(factory, blocks).build();
    }

    /**
     @param blockId 方块ID
     @return <code>new Identifier(<i>blockId</i>.getNamespace(), "block/" + <i>blockId</i>.getPath())</code>
     @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull Identifier prefixBlock(@NotNull Identifier blockId) {
        return new Identifier(blockId.getNamespace(), "block/" + blockId.getPath());
    }

    private Registries() {

    }
}
