package phoupraw.mcmod.createsdelight.api;

import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.tag.TagKey;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.recipe.MincingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PressureCookingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;

import java.util.function.Predicate;
import java.util.function.UnaryOperator;
public final class LambdasC {

    @Contract(pure = true)
    public static @NotNull Predicate<FluidVariant> matching(FluidIngredient predicate) {
        return v -> predicate.test(new FluidStack(v, Long.MAX_VALUE));
    }

    /**
     如果配方是{@link CuttingRecipe}或{@link CuttingBoardRecipe}，则匹配工具，否则返回{@code false}
     @param tool 工具物品，例如小刀、斧头等。
     */
    @Contract(pure = true)
    public static @NotNull Predicate<Recipe<?>> matchingTool(ItemStack tool) {
        return recipe -> {
            if (recipe instanceof CuttingRecipe cutting) return cutting.getIngredients().get(1).test(tool);
            if (recipe instanceof CuttingBoardRecipe cutting) return cutting.getTool().test(tool);
            return false;
        };
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> requiring(Ingredient ingredient) {
        return r -> r.require(ingredient);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> requiring(ItemConvertible item) {
        return r -> r.require(item);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> requiring(TagKey<Item> tag) {
        return r -> r.require(tag);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> requiring(FluidIngredient fluidIngredient) {
        return r -> r.require(fluidIngredient);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> requiring(Fluid fluid, long amount) {
        return r -> r.require(fluid, amount);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> during() {
        return ProcessingRecipeBuilder::averageProcessingDuration;
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> during(int ticks) {
        return r -> r.duration(ticks);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> outputing(float chance, ItemConvertible item) {
        return r -> r.output(chance, item);
    }

    @Contract(pure = true)
    public static <T extends ProcessingRecipe<?>> @NotNull UnaryOperator<ProcessingRecipeBuilder<T>> outputing(float chance, ItemConvertible item, int amount) {
        return r -> r.output(chance, item, amount);
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<FillingRecipe> newingfilling() {
        return FillingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<DeployerApplicationRecipe> newingDeploying() {
        return DeployerApplicationRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<CuttingRecipe> newingCutting() {
        return CuttingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<PanFryingRecipe> newingPanFrying() {
        return PanFryingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<CompactingRecipe> newingCompacting() {
        return CompactingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<SprinklingRecipe> newingSprinkling() {
        return SprinklingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<PressureCookingRecipe> newingPressureCooking() {
        return PressureCookingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<MincingRecipe> newingMincingRecipe() {
        return MincingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<MixingRecipe> newingMixing() {
        return MixingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<PressingRecipe> newingPressing() {
        return PressingRecipe::new;
    }

    @Contract(pure = true)
    public static ProcessingRecipeBuilder.@NotNull ProcessingRecipeFactory<MillingRecipe> newingMilling() {
        return MillingRecipe::new;
    }

    @Contract(pure = true)
    public static <T> @NotNull UnaryOperator<T> combine(@NotNull UnaryOperator<T> first, @NotNull UnaryOperator<T> second) {
        return t -> second.apply(first.apply(t));
    }

    private LambdasC() {}
}
