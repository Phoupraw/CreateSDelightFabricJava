package phoupraw.mcmod.createsdelight.datagen;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import phoupraw.mcmod.createsdelight.registry.CSDFluids;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

import java.util.List;
import java.util.function.Consumer;

public final class CSDRecipeProvider extends FabricRecipeProvider {

    public CSDRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerCompactingRecipe(exporter, RecipeCategory.FOOD, CSDItems.BUTTER_BLOCK, CSDItems.BUTTER_INGOT);
        offerCompactingRecipe(exporter, RecipeCategory.FOOD, CSDItems.BUTTER_INGOT, CSDItems.BUTTER_NUGGET);
        offerShapelessRecipe(exporter, CSDItems.BUTTER_NUGGET, CSDItems.BUTTER_INGOT, null, 9);
        offerShapelessRecipe(exporter, CSDItems.BUTTER_INGOT, CSDItems.BUTTER_BLOCK, null, 9);
        offerSmelting(exporter, List.of(Items.DRIED_KELP), RecipeCategory.FOOD, CSDItems.KELP_ASH, 0.2f, 200, null);
        offerBlasting(exporter, List.of(Items.DRIED_KELP), RecipeCategory.FOOD, CSDItems.KELP_ASH, 0.2f, 100, null);
        offerStonecuttingRecipe(exporter, RecipeCategory.FOOD, AllItems.BAR_OF_CHOCOLATE.get(), CSDItems.CHOCOLATE_BLOCK, 3);
        offerStonecuttingRecipe(exporter, RecipeCategory.FOOD, CSDItems.BUTTER_INGOT, CSDItems.BUTTER_BLOCK, 9);
        offerStonecuttingRecipe(exporter, RecipeCategory.FOOD, CSDItems.BUTTER_NUGGET, CSDItems.BUTTER_INGOT, 9);
        new ShapelessRecipeJsonBuilder(RecipeCategory.FOOD, CSDItems.CHOCOLATE_BLOCK, 3)
          .input(AllItems.BAR_OF_CHOCOLATE.get(), 9)
          .criterion("stupidMojang", conditionsFromItem(AllItems.BAR_OF_CHOCOLATE.get()))
          .offerTo(exporter, CSDIdentifiers.CHOCOLATE_BLOCK.withPrefixedPath("crafting/"));
        new ProcessingRecipeBuilder<>(EmptyingRecipe::new, CSDIdentifiers.EGG_LIQUID)
          .require(Items.EGG)
          .output(CSDItems.EGG_SHELL)
          .output(CSDFluids.EGG_LIQUID, FluidConstants.BOTTLE / 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(MixingRecipe::new, CSDIdentifiers.CREAM)
          .require(Milk.STILL_MILK, FluidConstants.BUCKET)
          .require(CSDItems.KELP_ASH)
          .output(CSDItems.BUTTER_NUGGET, 5)
          .output(Fluids.WATER, FluidConstants.BOTTLE)
          .output(CSDFluids.CREAM, FluidConstants.BOTTLE / 2)
          .duration(600)
          .build(exporter);
        new ProcessingRecipeBuilder<>(MixingRecipe::new, CSDIdentifiers.of(AllFluids.CHOCOLATE.getId().getPath()))
          .require(CSDItems.CHOCOLATE_BLOCK)
          .output(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(MixingRecipe::new, CSDIdentifiers.APPLE_JAM)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.SUGAR)
          .output(CSDFluids.APPLE_JAM, FluidConstants.BOTTLE * 2)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(MixingRecipe::new, CSDIdentifiers.WHEAT_PASTE)
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(CSDItems.KELP_ASH)
          .require(Fluids.WATER, FluidConstants.BOTTLE * 2)
          .output(CSDFluids.WHEAT_PASTE, FluidConstants.BUCKET / 2)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(CompactingRecipe::new, CSDIdentifiers.WHEAT_CAKE_BASE_BLOCK)
          .require(CSDFluids.WHEAT_PASTE, FluidConstants.BUCKET)
          .output(CSDItems.WHEAT_CAKE_BASE_BLOCK)
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        //TODO 半桶牛奶-密封发酵->半桶酸奶
        //TODO 1桶酸奶-压块塑形->奶酪瓣×1
        //TODO 奶酪瓣×4->奶酪块×1
    }

}
