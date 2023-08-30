package phoupraw.mcmod.createsdelight.datagen;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import phoupraw.mcmod.createsdelight.registry.CSDFluids;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

import java.util.function.Consumer;

public final class CSDRecipeProvider extends FabricRecipeProvider {


    public CSDRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerStonecuttingRecipe(exporter, RecipeCategory.FOOD, AllItems.BAR_OF_CHOCOLATE.get(), CSDItems.CHOCOLATE_BLOCK, 3);
        new ShapedRecipeJsonBuilder(RecipeCategory.FOOD, CSDItems.CHOCOLATE_BLOCK, 3)
          .input('A', AllItems.BAR_OF_CHOCOLATE.get())
          .pattern("AAA")
          .pattern("AAA")
          .pattern("AAA")
          .criterion("stupidMojang", conditionsFromItem(AllItems.BAR_OF_CHOCOLATE.get()))
          .offerTo(exporter, CSDIdentifiers.CHOCOLATE_BLOCK.withPrefixedPath("crafting/"));
        //new ProcessingRecipeBuilder<>(CompactingRecipe::new, CSDIdentifiers.CHOCOLATE_BLOCK)
        //  .require(AllItems.BAR_OF_CHOCOLATE.get())
        //  .require(AllItems.BAR_OF_CHOCOLATE.get())
        //  .require(AllItems.BAR_OF_CHOCOLATE.get())
        //  .output(CSDItems.CHOCOLATE_BLOCK)
        //  .build(exporter);
        new ProcessingRecipeBuilder<>(EmptyingRecipe::new, CSDIdentifiers.EGG_LIQUID)
          .require(Items.EGG)
          .output(CSDItems.EGG_SHELL)
          .output(CSDFluids.EGG_LIQUID, FluidConstants.BOTTLE / 2)
          .build(exporter);
        //offerStonecuttingRecipe(exporter, RecipeCategory.FOOD, CDItems.CAKE_BLUEPRINT, AllItems.SCHEMATIC.get(), 1);
        ////offerCookingRecipe(exporter, "cooking", RecipeSerializer.SMELTING, 20 * 10, Items.DRIED_KELP, CDItems.KELP_ASH, 0.2f);
        ////offerCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 20 * 10, CDItems.RAW_BASQUE_CAKE, CDItems.BASQUE_CAKE, 2f);
        //ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, CDItems.SWEET_BERRIES_CAKE)
        //  .pattern("A")
        //  .pattern("B")
        //  .pattern("C")
        //  .input('A', Items.SWEET_BERRIES)
        //  .input('B', ItemsRegistry.MILK_BOTTLE.get())
        //  .input('C', CDItems.CAKE_BASE_SLICE)
        //  .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
        //  .offerTo(exporter);
        //ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, CDItems.IRON_BOWL)
        //  .pattern("A A")
        //  .pattern(" A ")
        //  .input('A', AllItems.IRON_SHEET.get())
        //  .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
        //  .offerTo(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingfilling(), new Identifier(CreateSDelight.MOD_ID, "chocolate_pie"))
        //  .require(ItemsRegistry.PIE_CRUST.get())
        //  .require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET / 2)
        //  .output(ItemsRegistry.CHOCOLATE_PIE.get())
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.EGG_DOUGH)
        //  .require(AllItems.DOUGH.get())
        //  .require(Items.SUGAR)
        //  .require(CDFluids.EGG_LIQUID, FluidConstants.BOTTLE)
        //  .require(CDFluidTags.OIL, FluidConstants.BOTTLE / 2)
        //  .output(CDItems.EGG_DOUGH)
        //  .averageProcessingDuration()
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingCompacting(), CDIdentifiers.JELLY_BEANS)
        //  .require(Items.SUGAR)
        //  .require(Items.LIGHT_BLUE_DYE)
        //  .require(Items.PINK_DYE)
        //  .require(Items.YELLOW_DYE)
        //  .averageProcessingDuration()
        //  .output(CDItems.JELLY_BEANS)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.PASTE)
        //  .require(CDItems.KELP_ASH)
        //  .require(AllItems.WHEAT_FLOUR.get())
        //  .require(AllItems.WHEAT_FLOUR.get())
        //  .require(AllItems.WHEAT_FLOUR.get())
        //  .require(Items.SUGAR)
        //  .require(Items.SUGAR)
        //  .require(CDFluidTags.OIL, FluidConstants.BOTTLE / 2)
        //  .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
        //  .require(CDFluids.EGG_LIQUID, FluidConstants.BOTTLE)
        //  .averageProcessingDuration()
        //  .output(CDFluids.PASTE, FluidConstants.BUCKET)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingCutting(), CDIdentifiers.CAKE_BASE_SLICE)
        //  .require(CDItems.CAKE_BASE)
        //  .duration(20)
        //  .output(CDItems.CAKE_BASE_SLICE, 3)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingMilling(), CDIdentifiers.SUNFLOWER_OIL)
        //  .require(CDItems.SUNFLOWER_KERNELS)
        //  .duration(20)
        //  .output(CDFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 8)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingMilling(), CDIdentifiers.PUMPKIN_OIL)
        //  .require(Items.PUMPKIN_SEEDS)
        //  .averageProcessingDuration()
        //  .output(CDFluids.PUMPKIN_OIL, FluidConstants.BOTTLE / 8)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.APPLE_PASTE)
        //  .require(Items.APPLE)
        //  .require(Items.APPLE)
        //  .require(Items.APPLE)
        //  .require(Items.APPLE)
        //  .require(Ingredient.ofItems(Items.OAK_LEAVES, Items.DARK_OAK_LEAVES))
        //  .require(Ingredient.ofItems(Items.OAK_LEAVES, Items.DARK_OAK_LEAVES))
        //  .require(Ingredient.ofItems(Items.OAK_SAPLING, Items.DARK_OAK_SAPLING))
        //  .require(CDFluids.PASTE, FluidConstants.BOTTLE)
        //  .averageProcessingDuration()
        //  .output(CDFluids.APPLE_PASTE, FluidConstants.BOTTLE)
        //  .build(exporter);
        ////new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.MASHED_PATATO)
        ////  .require(Items.POTATO)
        ////  .require(Items.POTATO)
        ////  .require(Items.POTATO)
        ////  .require(CDItems.KELP_ASH)
        ////  .require(ItemsRegistry.ONION.get())
        ////  .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
        ////  .require(CDFluids.EGG_LIQUID, FluidConstants.BOTTLE)
        ////  .averageProcessingDuration()
        ////  .output(CDFluids.MASHED_POTATO, FluidConstants.BOTTLE * 2)
        ////  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingCompacting(), CDIdentifiers.IRON_BOWL)
        //  .require(Items.BUCKET)
        //  .output(CDItems.IRON_BOWL)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.CHOCOLATE_PASTE)
        //  .require(CDFluids.PASTE, FluidConstants.BOTTLE)
        //  .require(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE)
        //  .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
        //  .require(Items.COCOA_BEANS)
        //  .require(Items.COCOA_BEANS)
        //  .require(Items.SUGAR)
        //  .averageProcessingDuration()
        //  .output(CDFluids.CHOCOLATE_PASTE, FluidConstants.BUCKET)
        //  .build(exporter);
        //
        ////--------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.JELLY_BEANS_CAKE)
        //  .require(CDItems.JELLY_BEANS)
        //  .transitionTo(CDItems.JELLY_BEANS_CAKE_0)
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.JELLY_BEANS))
        //  .loops(3)
        //  .addOutput(CDItems.JELLY_BEANS_CAKE, 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.SWEET_BERRIES_CAKE)
        //  .require(CDItems.CAKE_BASE_SLICE)
        //  .transitionTo(CDItems.SWEET_BERRIES_CAKE_0)
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
        //  .loops(1)
        //  .addOutput(CDItems.SWEET_BERRIES_CAKE, 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.RAW_BASQUE_CAKE)
        //  .require(CDItems.CAKE_BASE)
        //  .transitionTo(CDItems.RAW_BASQUE_CAKE_0)
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SUGAR))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.TEA.get(), FluidConstants.BOTTLE))
        //  .loops(3)
        //  .addOutput(CDItems.RAW_BASQUE_CAKE, 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.SWEET_BERRIES_CAKE_S)
        //  .require(Items.SWEET_BERRIES)
        //  .transitionTo(CDItems.SWEET_BERRIES_CAKE_S_0)
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.SWEET_BERRIES_CAKE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.RED_DYE))
        //  .loops(3)
        //  .addOutput(CDItems.SWEET_BERRIES_CAKE_S, 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.BROWNIE)
        //  .require(AllItems.BAR_OF_CHOCOLATE.get())
        //  .transitionTo(CDItems.BROWNIE_0)
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingPressing(), UnaryOperator.identity())
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
        //  .addStep(LambdasC.newingCutting(), LambdasC.during(20))
        //  .loops(3)
        //  .addOutput(new ItemStack(CDItems.BROWNIE, 4), 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.APPLE_CREAM_CAKE)
        //  .require(Items.APPLE)
        //  .transitionTo(CDItems.APPLE_CREAM_CAKE_0)
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.APPLE))
        //  .addStep(LambdasC.newingCutting(), LambdasC.during(20))
        //  .loops(3)
        //  .addOutput(new ItemStack(CDItems.APPLE_CREAM_CAKE, 1), 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.SUNFLOWER_KERNELS)
        //  .require(Items.SUNFLOWER)
        //  .transitionTo(Items.SUNFLOWER)
        //  .addStep(LambdasC.newingCutting(), LambdasC.combine(LambdasC.during(20), LambdasC.outputing(0.25f, Items.STICK)))
        //  .addStep(LambdasC.newingPressing(), LambdasC.outputing(0.75f, Items.YELLOW_DYE))
        //  .loops(6)
        //  .addOutput(new ItemStack(CDItems.SUNFLOWER_KERNELS, 8), 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.CARROT_CREAM_CAKE)
        //  .require(Items.CARROT)
        //  .transitionTo(CDItems.CARROT_CREAM_CAKE_0)
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.CARROT))
        //  .addStep(LambdasC.newingCutting(), LambdasC.during(20))
        //  .loops(3)
        //  .addOutput(new ItemStack(CDItems.CARROT_CREAM_CAKE, 1), 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.SMALL_CHOCOLATE_CREAM_CAKE)
        //  .require(CDItems.CHOCOLATE_CAKE_BASE)
        //  .transitionTo(CDItems.CHOCOLATE_CAKE_BASE)
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
        //  .addStep(LambdasC.newingCutting(), LambdasC.during(20))
        //  .loops(3)
        //  .addOutput(new ItemStack(CDItems.SMALL_CHOCOLATE_CREAM_CAKE, 3), 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.MEDIUM_CHOCOLATE_CREAM_CAKE)
        //  .require(CDItems.CHOCOLATE_CAKE_BASE)
        //  .transitionTo(CDItems.CHOCOLATE_CAKE_BASE)
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.WHITE_DYE))
        //  .addStep(LambdasC.newingCutting(), LambdasC.during(20))
        //  .loops(3)
        //  .addOutput(new ItemStack(CDItems.MEDIUM_CHOCOLATE_CREAM_CAKE, 1), 1)
        //  .build(exporter);
        //new SequencedAssemblyRecipeBuilder(CDIdentifiers.BIG_CHOCOLATE_CREAM_CAKE)
        //  .require(CDItems.CHOCOLATE_CAKE_BASE)
        //  .transitionTo(CDItems.CHOCOLATE_CAKE_BASE)
        //  .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
        //  .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
        //  .addStep(LambdasC.newingCutting(), LambdasC.during(20))
        //  .loops(4)
        //  .addOutput(new ItemStack(CDItems.BIG_CHOCOLATE_CREAM_CAKE, 1), 1)
        //  .build(exporter);
        //
        ////蛋糕材料
        //new ProcessingRecipeBuilder<>(LambdasC.newingCompacting(), CDIdentifiers.MILK)
        //  .require(Milk.STILL_MILK, FluidConstants.BUCKET)
        //  .output(CDItems.MILK)
        //  .build(exporter);
        //new ProcessingRecipeBuilder<>(LambdasC.newingCompacting(), CDIdentifiers.CHOCOLATE)
        //  .require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET)
        //  .output(CDItems.CHOCOLATE)
        //  .build(exporter);

    }


}
