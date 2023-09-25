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
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
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
        offerSmelting(exporter, List.of(Items.DRIED_KELP), RecipeCategory.FOOD, CSDItems.KELP_ASH, 0.2f, 200, null);
        offerBlasting(exporter, List.of(Items.DRIED_KELP), RecipeCategory.FOOD, CSDItems.KELP_ASH, 0.2f, 100, null);
        offerStonecuttingRecipe(exporter, RecipeCategory.FOOD, AllItems.BAR_OF_CHOCOLATE.get(), CSDItems.CHOCOLATE_BLOCK, 3);
        new ShapedRecipeJsonBuilder(RecipeCategory.FOOD, CSDItems.CHOCOLATE_BLOCK, 3)
          .input('A', AllItems.BAR_OF_CHOCOLATE.get())
          .pattern("AAA")
          .pattern("AAA")
          .pattern("AAA")
          .criterion("stupidMojang", conditionsFromItem(AllItems.BAR_OF_CHOCOLATE.get()))
          .offerTo(exporter, CSDIdentifiers.CHOCOLATE_BLOCK.withPrefixedPath("crafting/"));
        new ProcessingRecipeBuilder<>(EmptyingRecipe::new, CSDIdentifiers.EGG_LIQUID)
          .require(Items.EGG)
          .output(CSDItems.EGG_SHELL)
          .output(CSDFluids.EGG_LIQUID, FluidConstants.BOTTLE / 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(CompactingRecipe::new, CSDIdentifiers.CREAM_BLOCK)
          .require(Milk.STILL_MILK, FluidConstants.BLOCK)
          .output(CSDItems.CREAM_BLOCK)
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
        new ProcessingRecipeBuilder<>(CompactingRecipe::new, CSDIdentifiers.APPLE_JAM_BLOCK)
          .require(CSDFluids.APPLE_JAM, FluidConstants.BUCKET)
          .output(CSDItems.APPLE_JAM_BLOCK)
          .build(exporter);
        new ProcessingRecipeBuilder<>(MixingRecipe::new, CSDIdentifiers.WHEAT_PASTE)
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(CSDItems.KELP_ASH)
          .require(Fluids.WATER, FluidConstants.BOTTLE * 2)
          .output(CSDFluids.APPLE_JAM, FluidConstants.BUCKET / 2)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(CompactingRecipe::new, CSDIdentifiers.WHEAT_PASTE_BLOCK)
          .require(CSDFluids.WHEAT_PASTE, FluidConstants.BUCKET)
          .output(CSDItems.WHEAT_PASTE_BLOCK)
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
    }

}
