package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.SmithingRecipeJsonBuilder;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.api.LambdasC;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SteamingRecipe;
import phoupraw.mcmod.createsdelight.recipe.VerticalCuttingRecipe;
import phoupraw.mcmod.createsdelight.registry.*;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
public final class MyRecipeProvider extends FabricRecipeProvider {
    public MyRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerStonecuttingRecipe(exporter, CDItems.THICK_PORK_SLICE, Items.PORKCHOP, 2);
        offerStonecuttingRecipe(exporter, CDItems.THIN_PORK_SLICE, CDItems.THICK_PORK_SLICE, 2);
        offerStonecuttingRecipe(exporter, CDItems.SKEWER, AllBlocks.SHAFT.get(), 1);
        offerStonecuttingRecipe(exporter, CDItems.BASIN, AllBlocks.BASIN.get(), 1);
        offerStonecuttingRecipe(exporter, CDItems.CAKE_BLUEPRINT, AllItems.SCHEMATIC.get(), 1);
        offerCookingRecipe(exporter, "cooking", RecipeSerializer.SMELTING, 20 * 20, Items.WATER_BUCKET, CDItems.SALT, 0.4f);
        offerCookingRecipe(exporter, "cooking", RecipeSerializer.SMELTING, 20 * 10, Items.DRIED_KELP, CDItems.KELP_ASH, 0.2f);
        offerCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 20 * 10, CDItems.RAW_BASQUE_CAKE, CDItems.BASQUE_CAKE, 2f);
        ShapedRecipeJsonBuilder.create(CDItems.SPRINKLER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.SHAFT.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', Items.IRON_BARS)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.BAMBOO_STEAMER)
          .pattern("A")
          .pattern("B")
          .input('A', ItemsRegistry.BASKET.get())
          .input('B', Items.SCAFFOLDING)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.COPPER_TUNNEL)
          .pattern("AA")
          .pattern("BB")
          .input('A', AllItems.COPPER_SHEET.get())
          .input('B', Items.DRIED_KELP)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.VERTICAL_CUTTER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.SHAFT.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', AllItems.IRON_SHEET.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.PRESSURE_COOKER)
          .pattern("A")
          .pattern("B")
          .input('A', Items.DRIED_KELP)
          .input('B', AllBlocks.MECHANICAL_PISTON.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.MINCER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.COGWHEEL.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', AllItems.PROPELLER.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.SKEWER_PLATE)
          .pattern("A")
          .pattern("B")
          .input('A', CDItems.SKEWER)
          .input('B', AllBlocks.TURNTABLE.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.SWEET_BERRIES_CAKE)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', Items.SWEET_BERRIES)
          .input('B', ItemsRegistry.MILK_BOTTLE.get())
          .input('C', CDItems.CAKE_BASE_SLICE)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(CDItems.OVEN)
          .group("oven")
          .pattern("ACA")
          .pattern("B B")
          .pattern("ACA")
          .input('A', AllItems.ANDESITE_ALLOY.get())
          .input('B', Items.GLASS_PANE)
          .input('C', AllItems.IRON_SHEET.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter, CDIdentifiers.of("crafting/oven_1"));
        ShapedRecipeJsonBuilder.create(CDItems.OVEN)
          .group("oven")
          .pattern("A")
          .pattern("B")
          .input('A', AllBlocks.FLUID_TANK.get())
          .input('B', AllBlocks.BASIN.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter, CDIdentifiers.of("crafting/oven_2"));
        ShapedRecipeJsonBuilder.create(CDItems.IRON_BOWL)
          .pattern("A A")
          .pattern(" A ")
          .input('A', AllItems.IRON_SHEET.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        SmithingRecipeJsonBuilder.create(
            Ingredient.ofItems(AllBlocks.ITEM_DRAIN.get()),
            Ingredient.ofItems(AllItems.ELECTRON_TUBE.get()),
            CDItems.SMART_DRAIN)
          .criterion("", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter, CDIdentifiers.SMART_DRAIN);
        SmithingRecipeJsonBuilder.create(
            Ingredient.ofItems(AllBlocks.BASIN.get()),
            Ingredient.ofItems(AllItems.ELECTRON_TUBE.get()),
            CDItems.MULTIFUNC_BASIN)
          .criterion("", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter, CDIdentifiers.MULTIFUNC_BASIN);

//        exporter.accept(new CuttingBoardRecipeJsonProvider(new CuttingBoardRecipe(MyIdentifiers.THICK_PORK_SLICE, "", Ingredient.ofItems(Items.PORKCHOP), Ingredient.fromTag(TagsRegistry.STRAW_HARVESTERS), DefaultedList.copyOf(ChanceResult.EMPTY, new ChanceResult(new ItemStack(MyItems.THICK_PORK_SLICE, 2), 1)), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.name())));
//        exporter.accept(new CuttingBoardRecipeJsonProvider(new CuttingBoardRecipe(MyIdentifiers.THIN_PORK_SLICE, "", Ingredient.ofItems(MyItems.THICK_PORK_SLICE), Ingredient.fromTag(TagsRegistry.STRAW_HARVESTERS), DefaultedList.copyOf(ChanceResult.EMPTY, new ChanceResult(new ItemStack(MyItems.THIN_PORK_SLICE, 2), 1)), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.name())));

        new ProcessingRecipeBuilder<>(LambdasC.newingfilling(), new Identifier(CreateSDelight.MOD_ID, "chocolate_pie"))
          .require(ItemsRegistry.PIE_CRUST.get())
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET / 2)
          .output(ItemsRegistry.CHOCOLATE_PIE.get())
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingCutting(), CDIdentifiers.PAN)
          .require(ItemsRegistry.SKILLET.get())
          .output(CDItems.PAN)
          .output(Items.BRICK)
          .duration(40)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPanFrying(), CDIdentifiers.PAN_FRIED_BEEF_PATTY)
          .require(ItemsRegistry.MINCED_BEEF.get())
          .require(CDFluidTags.OIL, FluidConstants.BOTTLE / 10)
          .output(CDItems.PAN_FRIED_BEEF_PATTY)
          .duration(100)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingCutting(), CDIdentifiers.GRILL)
          .require(ItemsRegistry.STOVE.get())
          .output(CDItems.GRILL)
          .output(Items.BRICKS, 4)
          .output(Items.CAMPFIRE)
          .duration(40)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPanFrying(), CDIdentifiers.PAN_FRIED_PORK_SLICE)
          .require(CDItems.THICK_PORK_SLICE)
          .output(CDItems.PAN_FRIED_PORK_SLICE)
          .duration(140)
          .build(exporter);
        new ProcessingRecipeBuilder<>(GrillingRecipe::new, CDIdentifiers.GRILLED_PORK_SLICE)
          .require(CDItems.THIN_PORK_SLICE)
          .output(CDItems.GRILLED_PORK_SLICE)
          .duration(60)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingSprinkling(), CDIdentifiers.SUGAR_PORK)
          .require(CDItemTags.COOKED_PORK)
          .require(Items.SUGAR)
          .output(CDItems.SUGAR_PORK)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingSprinkling(), CDIdentifiers.LEAVES_RICE)
          .require(ItemsRegistry.COOKED_RICE.get())
          .require(ItemTags.LEAVES)
          .output(CDItems.LEAVES_RICE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPanFrying(), CDIdentifiers.VANILLA)
          .require(Items.GRASS)
          .require(CDFluidTags.OIL, FluidConstants.BOTTLE / 10)
          .output(CDItems.VANILLA)
          .duration(60)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingSprinkling(), CDIdentifiers.VANILLA_SWEET_ROLL)
          .require(AllItems.SWEET_ROLL.get())
          .require(CDItems.VANILLA)
          .output(CDItems.VANILLA_SWEET_ROLL)
          .build(exporter);
        new ProcessingRecipeBuilder<>(SteamingRecipe::new, CDIdentifiers.STEAMED_BUNS)
          .require(CDItemTags.DOUGH)
          .output(CDItems.STEAMED_BUNS)
          .duration(200)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.CAKE_SLICE.get()))
          .withKnives(3)
          .require(Items.CAKE)
          .output(ItemsRegistry.CAKE_SLICE.get(), 7)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get()))
          .withKnives(2)
          .require(ItemsRegistry.SWEET_BERRY_CHEESECAKE.get())
          .output(ItemsRegistry.SWEET_BERRY_CHEESECAKE_SLICE.get(), 4)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.CHOCOLATE_PIE_SLICE.get()))
          .withKnives(2)
          .require(ItemsRegistry.CHOCOLATE_PIE.get())
          .output(ItemsRegistry.CHOCOLATE_PIE_SLICE.get(), 4)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.APPLE_PIE_SLICE.get()))
          .withKnives(2)
          .require(ItemsRegistry.APPLE_PIE.get())
          .output(ItemsRegistry.APPLE_PIE_SLICE.get(), 4)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(Items.PORKCHOP))
          .withKnives(2)
          .require(ItemsRegistry.HAM.get())
          .output(Items.PORKCHOP, 2)
          .output(Items.BONE)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(Items.COOKED_PORKCHOP))
          .withKnives(2)
          .require(ItemsRegistry.SMOKED_HAM.get())
          .output(Items.COOKED_PORKCHOP, 2)
          .output(Items.BONE)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.SALMON_SLICE.get()))
          .withKnives(2)
          .require(Items.SALMON)
          .output(ItemsRegistry.SALMON_SLICE.get(), 2)
          .output(Items.BONE_MEAL)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.COOKED_SALMON_SLICE.get()))
          .withKnives(2)
          .require(Items.COOKED_SALMON)
          .output(ItemsRegistry.COOKED_SALMON_SLICE.get(), 2)
          .output(Items.BONE_MEAL)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.COD_SLICE.get()))
          .withKnives(2)
          .require(Items.COOKED_COD)
          .output(ItemsRegistry.COD_SLICE.get(), 2)
          .output(Items.BONE_MEAL)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.COOKED_COD_SLICE.get()))
          .withKnives(2)
          .require(Items.COD)
          .output(ItemsRegistry.COOKED_COD_SLICE.get(), 2)
          .output(Items.BONE_MEAL)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.BACON.get()))
          .withKnives(1)
          .require(Items.PORKCHOP)
          .output(ItemsRegistry.BACON.get(), 2)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.MUTTON_CHOPS.get()))
          .withKnives(1)
          .require(Items.MUTTON)
          .output(ItemsRegistry.MUTTON_CHOPS.get(), 2)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.COOKED_MUTTON_CHOPS.get()))
          .withKnives(1)
          .require(Items.COOKED_MUTTON)
          .output(ItemsRegistry.COOKED_MUTTON_CHOPS.get(), 2)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.CHICKEN_CUTS.get()))
          .withKnives(1)
          .require(Items.CHICKEN)
          .output(ItemsRegistry.CHICKEN_CUTS.get(), 2)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.COOKED_CHICKEN_CUTS.get()))
          .withKnives(1)
          .require(Items.COOKED_CHICKEN)
          .output(ItemsRegistry.COOKED_CHICKEN_CUTS.get(), 2)
          .build(exporter);
        new VerticalCuttingRecipe.Builder(Registry.ITEM.getId(ItemsRegistry.MINCED_BEEF.get()))
          .withKnives(16)
          .require(Items.BEEF)
          .output(ItemsRegistry.MINCED_BEEF.get(), 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.COOKED_RICE)
          .require(ItemsRegistry.RICE.get())
          .require(Fluids.WATER, FluidConstants.BUCKET / 9)
          .output(CDItems.COOKED_RICE)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.VEGETABLE_BIG_STEW)
          .require(Items.CARROT)
          .require(Items.POTATO)
          .require(Items.BEETROOT)
          .require(ItemsRegistry.TOMATO.get())
          .require(ItemsRegistry.CABBAGE_LEAF.get())
          .require(ItemsRegistry.ONION.get())
          .require(ItemsRegistry.PUMPKIN_SLICE.get())
          //          .require(Items.SUGAR)
          .require(Fluids.WATER, FluidConstants.BUCKET / 2)
          .output(CDFluids.VEGETABLE_BIG_STEW, FluidConstants.BUCKET / 2)
          .duration(20 * 15)
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), Registry.ITEM.getId(ItemsRegistry.MINCED_BEEF.get()))
          .require(Items.BEEF)
          .output(ItemsRegistry.MINCED_BEEF.get(), 2)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.ROSE_MILK_TEA)
          .require(Items.ROSE_BUSH)
          .require(Items.SUGAR)
          .require(ItemTags.LEAVES)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .output(CDFluids.ROSE_MILK_TEA, FluidConstants.BOTTLE)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.CORAL_COLORFULS)
          .require(Items.TUBE_CORAL_FAN)
          .require(Items.FIRE_CORAL_FAN)
          .require(Items.HORN_CORAL_FAN)
          .require(Items.SUGAR)
          .require(Items.WHITE_DYE)
          .require(AllFluids.TEA.get(), FluidConstants.BOTTLE)
          .output(CDItems.CORAL_COLORFULS)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), CDIdentifiers.TOMATO_SAUCE)
          .require(ItemsRegistry.TOMATO.get())
          .output(CDFluids.TOMATO_SAUCE, FluidConstants.BUCKET / 8)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), CDIdentifiers.POPPY_RUSSIAN_SOUP)
          .require(Items.POPPY)
          .require(Items.POPPY)
          .require(Items.POPPY)
          .require(Items.CARROT)
          .require(Items.BAKED_POTATO)
          .require(ItemsRegistry.MINCED_BEEF.get())
          .require(ItemsRegistry.CABBAGE_LEAF.get())
          .require(CDFluids.TOMATO_SAUCE, FluidConstants.BOTTLE)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE / 3)
          .require(CDFluids.BEETROOT_SOUP, FluidConstants.BOTTLE * 2 / 3)
          .output(CDFluids.POPPY_RUSSIAN_SOUP, FluidConstants.BOTTLE * 2)
          .duration(20 * 15)
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.EGG_DOUGH)
          .require(AllItems.DOUGH.get())
          .require(Items.SUGAR)
          .require(CDFluids.EGG_LIQUID, FluidConstants.BOTTLE)
          .require(CDFluidTags.OIL, FluidConstants.BOTTLE / 2)
          .output(CDItems.EGG_DOUGH)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), CDIdentifiers.CRUSHED_ICE)
          .require(Items.ICE)
          .require(Items.PACKED_ICE)
          .require(Items.BLUE_ICE)
          .require(Items.SNOWBALL)
          .require(AllFluids.HONEY.get(), FluidConstants.BOTTLE)
          .output(CDItems.CRUSHED_ICE, 3)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.WHEAT_BLACK_TEA)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.BLACK_DYE)
          .require(CDItems.VANILLA)
          .require(Fluids.WATER, FluidConstants.BOTTLE)
          .output(CDItems.WHEAT_BLACK_TEA)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), CDIdentifiers.MELON_JUICE)
          .require(Items.MELON_SLICE)
          .require(Items.MELON_SLICE)
          .require(Items.MELON_SLICE)
          .require(Items.MELON_SLICE)
          .require(Items.SUGAR)
          .averageProcessingDuration()
          .output(CDFluids.MELON_JUICE, FluidConstants.BOTTLE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), CDIdentifiers.ICED_MELON_JUICE)
          .require(CDFluids.MELON_JUICE, FluidConstants.BOTTLE)
          .require(ItemsRegistry.MELON_POPSICLE.get())
          .require(CDItems.CRUSHED_ICE)
          .require(Items.NETHER_WART)
          .averageProcessingDuration()
          .output(CDFluids.ICED_MELON_JUICE, FluidConstants.BUCKET / 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMincingRecipe(), CDIdentifiers.THICK_HOT_COCOA)
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .require(CDItems.VANILLA)
          .require(AllItems.CHOCOLATE_BERRIES.get())
          .require(AllItems.BAR_OF_CHOCOLATE.get())
          .requiresHeat(HeatCondition.HEATED)
          .averageProcessingDuration()
          .output(CDFluids.THICK_HOT_COCOA, FluidConstants.BOTTLE * 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.SALT)
          .require(Fluids.WATER, FluidConstants.BUCKET)
          .requiresHeat(HeatCondition.HEATED)
          .duration(20 * 15)
          .output(CDItems.SALT)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingCompacting(), CDIdentifiers.JELLY_BEANS)
          .require(Items.SUGAR)
          .require(Items.LIGHT_BLUE_DYE)
          .require(Items.PINK_DYE)
          .require(Items.YELLOW_DYE)
          .averageProcessingDuration()
          .output(CDItems.JELLY_BEANS)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingPressureCooking(), CDIdentifiers.YEAST)
          .require(Items.SUGAR)
          .require(AllItems.DOUGH.get())
          .require(CDItems.KELP_ASH)
          .duration(20 * 60)
          .output(CDItems.YEAST, 5)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.PASTE)
          .require(CDItems.KELP_ASH)
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(Items.SUGAR)
          .require(Items.SUGAR)
          .require(CDFluidTags.OIL, FluidConstants.BOTTLE / 2)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .require(CDFluids.EGG_LIQUID, FluidConstants.BOTTLE)
          .averageProcessingDuration()
          .output(CDFluids.PASTE, FluidConstants.BUCKET)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingCutting(), CDIdentifiers.CAKE_BASE_SLICE)
          .require(CDItems.CAKE_BASE)
          .duration(20)
          .output(CDItems.CAKE_BASE_SLICE, 3)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMilling(), CDIdentifiers.SUNFLOWER_OIL)
          .require(CDItems.SUNFLOWER_KERNELS)
          .duration(20)
          .output(CDFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 8)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMilling(), CDIdentifiers.PUMPKIN_OIL)
          .require(Items.PUMPKIN_SEEDS)
          .averageProcessingDuration()
          .output(CDFluids.PUMPKIN_OIL, FluidConstants.BOTTLE / 8)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.APPLE_PASTE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Items.APPLE)
          .require(Ingredient.ofItems(Items.OAK_LEAVES, Items.DARK_OAK_LEAVES))
          .require(Ingredient.ofItems(Items.OAK_LEAVES, Items.DARK_OAK_LEAVES))
          .require(Ingredient.ofItems(Items.OAK_SAPLING, Items.DARK_OAK_SAPLING))
          .require(CDFluids.PASTE, FluidConstants.BOTTLE)
          .averageProcessingDuration()
          .output(CDFluids.APPLE_PASTE, FluidConstants.BOTTLE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.MASHED_PATATO)
          .require(Items.POTATO)
          .require(Items.POTATO)
          .require(Items.POTATO)
          .require(CDItems.KELP_ASH)
          .require(ItemsRegistry.ONION.get())
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .require(CDFluids.EGG_LIQUID, FluidConstants.BOTTLE)
          .averageProcessingDuration()
          .output(CDFluids.MASHED_POTATO, FluidConstants.BOTTLE * 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingCompacting(), CDIdentifiers.IRON_BOWL)
          .require(Items.BUCKET)
          .output(CDItems.IRON_BOWL)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingBaking(), CDIdentifiers.CAKE_BASE)
          .require(CDFluids.PASTE, FluidConstants.BUCKET / 2)
          .duration(20 * 10)
          .output(CDItems.CAKE_BASE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingBaking(), CDIdentifiers.APPLE_CAKE)
          .require(CDFluids.APPLE_PASTE, FluidConstants.BUCKET / 2)
          .duration(20 * 10)
          .output(CDItems.APPLE_CAKE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingMixing(), CDIdentifiers.CHOCOLATE_PASTE)
          .require(CDFluids.PASTE, FluidConstants.BOTTLE)
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .require(Items.COCOA_BEANS)
          .require(Items.COCOA_BEANS)
          .require(Items.SUGAR)
          .averageProcessingDuration()
          .output(CDFluids.CHOCOLATE_PASTE, FluidConstants.BUCKET)
          .build(exporter);
        new ProcessingRecipeBuilder<>(LambdasC.newingBaking(), CDIdentifiers.CHOCOLATE_CAKE_BASE)
          .require(CDFluids.CHOCOLATE_PASTE, FluidConstants.BUCKET / 2)
          .duration(20 * 10)
          .output(CDItems.CHOCOLATE_CAKE_BASE)
          .build(exporter);

        //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        new SequencedAssemblyRecipeBuilder(CDIdentifiers.JELLY_BEANS_CAKE)
          .require(CDItems.JELLY_BEANS)
          .transitionTo(CDItems.JELLY_BEANS_CAKE_0)
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.JELLY_BEANS))
          .loops(3)
          .addOutput(CDItems.JELLY_BEANS_CAKE, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.SWEET_BERRIES_CAKE)
          .require(CDItems.CAKE_BASE_SLICE)
          .transitionTo(CDItems.SWEET_BERRIES_CAKE_0)
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
          .loops(1)
          .addOutput(CDItems.SWEET_BERRIES_CAKE, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.RAW_BASQUE_CAKE)
          .require(CDItems.CAKE_BASE)
          .transitionTo(CDItems.RAW_BASQUE_CAKE_0)
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SUGAR))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.TEA.get(), FluidConstants.BOTTLE))
          .loops(3)
          .addOutput(CDItems.RAW_BASQUE_CAKE, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.SWEET_BERRIES_CAKE_S)
          .require(Items.SWEET_BERRIES)
          .transitionTo(CDItems.SWEET_BERRIES_CAKE_S_0)
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.SWEET_BERRIES_CAKE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.RED_DYE))
          .loops(3)
          .addOutput(CDItems.SWEET_BERRIES_CAKE_S, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.BROWNIE)
          .require(AllItems.BAR_OF_CHOCOLATE.get())
          .transitionTo(CDItems.BROWNIE_0)
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
          .addStep(LambdasC.newingPressing(), UnaryOperator.identity())
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(LambdasC.newingCutting(), LambdasC.during(20))
          .loops(3)
          .addOutput(new ItemStack(CDItems.BROWNIE, 4), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.APPLE_CREAM_CAKE)
          .require(Items.APPLE)
          .transitionTo(CDItems.APPLE_CREAM_CAKE_0)
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.APPLE))
          .addStep(LambdasC.newingCutting(), LambdasC.during(20))
          .loops(3)
          .addOutput(new ItemStack(CDItems.APPLE_CREAM_CAKE, 1), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.SUNFLOWER_KERNELS)
          .require(Items.SUNFLOWER)
          .transitionTo(Items.SUNFLOWER)
          .addStep(LambdasC.newingCutting(), LambdasC.combine(LambdasC.during(20), LambdasC.outputing(0.25f, Items.STICK)))
          .addStep(LambdasC.newingPressing(), LambdasC.outputing(0.75f, Items.YELLOW_DYE))
          .loops(6)
          .addOutput(new ItemStack(CDItems.SUNFLOWER_KERNELS, 8), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.CARROT_CREAM_CAKE)
          .require(Items.CARROT)
          .transitionTo(CDItems.CARROT_CREAM_CAKE_0)
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(CDItems.CAKE_BASE_SLICE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.CARROT))
          .addStep(LambdasC.newingCutting(), LambdasC.during(20))
          .loops(3)
          .addOutput(new ItemStack(CDItems.CARROT_CREAM_CAKE, 1), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.SMALL_CHOCOLATE_CREAM_CAKE)
          .require(CDItems.CHOCOLATE_CAKE_BASE)
          .transitionTo(CDItems.CHOCOLATE_CAKE_BASE)
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
          .addStep(LambdasC.newingCutting(), LambdasC.during(20))
          .loops(3)
          .addOutput(new ItemStack(CDItems.SMALL_CHOCOLATE_CREAM_CAKE, 3), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.MEDIUM_CHOCOLATE_CREAM_CAKE)
          .require(CDItems.CHOCOLATE_CAKE_BASE)
          .transitionTo(CDItems.CHOCOLATE_CAKE_BASE)
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.WHITE_DYE))
          .addStep(LambdasC.newingCutting(), LambdasC.during(20))
          .loops(3)
          .addOutput(new ItemStack(CDItems.MEDIUM_CHOCOLATE_CREAM_CAKE, 1), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(CDIdentifiers.BIG_CHOCOLATE_CREAM_CAKE)
          .require(CDItems.CHOCOLATE_CAKE_BASE)
          .transitionTo(CDItems.CHOCOLATE_CAKE_BASE)
          .addStep(LambdasC.newingfilling(), LambdasC.requiring(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(Items.SWEET_BERRIES))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(LambdasC.newingDeploying(), LambdasC.requiring(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(LambdasC.newingCutting(), LambdasC.during(20))
          .loops(4)
          .addOutput(new ItemStack(CDItems.BIG_CHOCOLATE_CREAM_CAKE, 1), 1)
          .build(exporter);
    }
}
