package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
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
import phoupraw.mcmod.createsdelight.recipe.*;
import phoupraw.mcmod.createsdelight.registry.MyFluids;
import phoupraw.mcmod.createsdelight.registry.MyIdentifiers;
import phoupraw.mcmod.createsdelight.registry.MyItemTags;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;
public final class MyRecipeProvider extends FabricRecipeProvider {
    public MyRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerStonecuttingRecipe(exporter, MyItems.THICK_PORK_SLICE, Items.PORKCHOP, 2);
        offerStonecuttingRecipe(exporter, MyItems.THIN_PORK_SLICE, MyItems.THICK_PORK_SLICE, 2);
        offerStonecuttingRecipe(exporter, MyItems.SKEWER, AllBlocks.SHAFT.get(), 1);
        offerStonecuttingRecipe(exporter, MyItems.BASIN, AllBlocks.BASIN.get(), 1);
        offerCookingRecipe(exporter, "cooking", RecipeSerializer.SMELTING, 20 * 20, Items.WATER_BUCKET, MyItems.SALT, 0.4f);
        offerCookingRecipe(exporter, "cooking", RecipeSerializer.SMELTING, 20 * 10, Items.DRIED_KELP, MyItems.KELP_ASH, 0.2f);
        offerCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 20 * 10, MyItems.RAW_BASQUE_CAKE, MyItems.BASQUE_CAKE, 2f);
        ShapedRecipeJsonBuilder.create(MyItems.SPRINKLER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.SHAFT.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', Items.IRON_BARS)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.BAMBOO_STEAMER)
          .pattern("A")
          .pattern("B")
          .input('A', ItemsRegistry.BASKET.get())
          .input('B', Items.SCAFFOLDING)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.COPPER_TUNNEL)
          .pattern("AA")
          .pattern("BB")
          .input('A', AllItems.COPPER_SHEET.get())
          .input('B', Items.DRIED_KELP)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.VERTICAL_CUTTER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.SHAFT.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', AllItems.IRON_SHEET.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.PRESSURE_COOKER)
          .pattern("A")
          .pattern("B")
          .input('A', Items.DRIED_KELP)
          .input('B', AllBlocks.MECHANICAL_PISTON.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.MINCER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.COGWHEEL.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', AllItems.PROPELLER.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.SKEWER_PLATE)
          .pattern("A")
          .pattern("B")
          .input('A', MyItems.SKEWER)
          .input('B', AllBlocks.TURNTABLE.get())
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(MyItems.SWEET_BERRIES_CAKE)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', Items.SWEET_BERRIES)
          .input('B', ItemsRegistry.MILK_BOTTLE.get())
          .input('C', MyItems.CAKE_BASE_SLICE)
          .criterion("stupidMojang", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter);
        SmithingRecipeJsonBuilder.create(
            Ingredient.ofItems(AllBlocks.ITEM_DRAIN.get()),
            Ingredient.ofItems(AllItems.ELECTRON_TUBE.get()),
            MyItems.SMART_DRAIN)
          .criterion("", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter, MyIdentifiers.SMART_DRAIN);
        SmithingRecipeJsonBuilder.create(
            Ingredient.ofItems(AllBlocks.BASIN.get()),
            Ingredient.ofItems(AllItems.ELECTRON_TUBE.get()),
            MyItems.MULTIFUNC_BASIN)
          .criterion("", conditionsFromItem(Items.CRAFTING_TABLE))
          .offerTo(exporter, MyIdentifiers.MULTIFUNC_BASIN);

//        exporter.accept(new CuttingBoardRecipeJsonProvider(new CuttingBoardRecipe(MyIdentifiers.THICK_PORK_SLICE, "", Ingredient.ofItems(Items.PORKCHOP), Ingredient.fromTag(TagsRegistry.STRAW_HARVESTERS), DefaultedList.copyOf(ChanceResult.EMPTY, new ChanceResult(new ItemStack(MyItems.THICK_PORK_SLICE, 2), 1)), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.name())));
//        exporter.accept(new CuttingBoardRecipeJsonProvider(new CuttingBoardRecipe(MyIdentifiers.THIN_PORK_SLICE, "", Ingredient.ofItems(MyItems.THICK_PORK_SLICE), Ingredient.fromTag(TagsRegistry.STRAW_HARVESTERS), DefaultedList.copyOf(ChanceResult.EMPTY, new ChanceResult(new ItemStack(MyItems.THIN_PORK_SLICE, 2), 1)), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.name())));

        ProcessingRecipeBuilder.ProcessingRecipeFactory<FillingRecipe> newingFilling = FillingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<DeployerApplicationRecipe> newingDeploying = DeployerApplicationRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<CuttingRecipe> newingCutting = CuttingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<PanFryingRecipe> newingPanFrying = PanFryingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<CompactingRecipe> newingCompacting = CompactingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<SprinklingRecipe> newingSprinkling = SprinklingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<PressureCookingRecipe> newingPressureCooking = PressureCookingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<MincingRecipe> newingMincingRecipe = MincingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<MixingRecipe> newingMixing = MixingRecipe::new;
        new ProcessingRecipeBuilder<>(newingFilling, new Identifier(CreateSDelight.MOD_ID, "chocolate_pie"))
          .require(ItemsRegistry.PIE_CRUST.get())
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET / 2)
          .output(ItemsRegistry.CHOCOLATE_PIE.get())
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingCutting, MyIdentifiers.PAN)
          .require(ItemsRegistry.SKILLET.get())
          .output(MyItems.PAN)
          .output(Items.BRICK)
          .duration(40)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPanFrying, MyIdentifiers.PAN_FRIED_BEEF_PATTY)
          .require(ItemsRegistry.MINCED_BEEF.get())
          .require(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 10)
          .output(MyItems.PAN_FRIED_BEEF_PATTY)
          .duration(100)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingCompacting, MyIdentifiers.SUNFLOWER_OIL)
          .require(Items.SUNFLOWER)
          .output(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingCutting, MyIdentifiers.GRILL)
          .require(ItemsRegistry.STOVE.get())
          .output(MyItems.GRILL)
          .output(Items.BRICKS, 4)
          .output(Items.CAMPFIRE)
          .duration(40)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPanFrying, MyIdentifiers.PAN_FRIED_PORK_SLICE)
          .require(MyItems.THICK_PORK_SLICE)
          .output(MyItems.PAN_FRIED_PORK_SLICE)
          .duration(140)
          .build(exporter);
        new ProcessingRecipeBuilder<>(GrillingRecipe::new, MyIdentifiers.GRILLED_PORK_SLICE)
          .require(MyItems.THIN_PORK_SLICE)
          .output(MyItems.GRILLED_PORK_SLICE)
          .duration(60)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingSprinkling, MyIdentifiers.SUGAR_PORK)
          .require(MyItemTags.COOKED_PORK)
          .require(Items.SUGAR)
          .output(MyItems.SUGAR_PORK)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingSprinkling, MyIdentifiers.LEAVES_RICE)
          .require(ItemsRegistry.COOKED_RICE.get())
          .require(ItemTags.LEAVES)
          .output(MyItems.LEAVES_RICE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPanFrying, MyIdentifiers.VANILLA)
          .require(Items.GRASS)
          .require(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 10)
          .output(MyItems.VANILLA)
          .duration(60)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingSprinkling, MyIdentifiers.VANILLA_SWEET_ROLL)
          .require(AllItems.SWEET_ROLL.get())
          .require(MyItems.VANILLA)
          .output(MyItems.VANILLA_SWEET_ROLL)
          .build(exporter);
        new ProcessingRecipeBuilder<>(SteamingRecipe::new, MyIdentifiers.STEAMED_BUNS)
          .require(MyItemTags.DOUGH)
          .output(MyItems.STEAMED_BUNS)
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
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.COOKED_RICE)
          .require(ItemsRegistry.RICE.get())
          .require(Fluids.WATER, FluidConstants.BUCKET / 9)
          .output(MyItems.COOKED_RICE)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.VEGETABLE_BIG_STEW)
          .require(Items.CARROT)
          .require(Items.POTATO)
          .require(Items.BEETROOT)
          .require(ItemsRegistry.TOMATO.get())
          .require(ItemsRegistry.CABBAGE_LEAF.get())
          .require(ItemsRegistry.ONION.get())
          .require(ItemsRegistry.PUMPKIN_SLICE.get())
//          .require(Items.SUGAR)
          .require(Fluids.WATER, FluidConstants.BUCKET / 2)
          .output(MyFluids.VEGETABLE_BIG_STEW, FluidConstants.BUCKET / 2)
          .duration(20 * 15)
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, Registry.ITEM.getId(ItemsRegistry.MINCED_BEEF.get()))
          .require(Items.BEEF)
          .output(ItemsRegistry.MINCED_BEEF.get(), 2)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.ROSE_MILK_TEA)
          .require(Items.ROSE_BUSH)
          .require(Items.SUGAR)
          .require(ItemTags.LEAVES)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .output(MyFluids.ROSE_MILK_TEA, FluidConstants.BOTTLE)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.CORAL_COLORFULS)
          .require(Items.TUBE_CORAL_FAN)
          .require(Items.FIRE_CORAL_FAN)
          .require(Items.HORN_CORAL_FAN)
          .require(Items.SUGAR)
          .require(Items.WHITE_DYE)
          .require(AllFluids.TEA.get(), FluidConstants.BOTTLE)
          .output(MyItems.CORAL_COLORFULS)
          .averageProcessingDuration()
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, MyIdentifiers.TOMATO_SAUCE)
          .require(ItemsRegistry.TOMATO.get())
          .output(MyFluids.TOMATO_SAUCE, FluidConstants.BUCKET / 8)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, MyIdentifiers.POPPY_RUSSIAN_SOUP)
          .require(Items.POPPY)
          .require(Items.POPPY)
          .require(Items.POPPY)
          .require(Items.CARROT)
          .require(Items.BAKED_POTATO)
          .require(ItemsRegistry.MINCED_BEEF.get())
          .require(ItemsRegistry.CABBAGE_LEAF.get())
          .require(MyFluids.TOMATO_SAUCE, FluidConstants.BOTTLE)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE / 3)
          .require(MyFluids.BEETROOT_SOUP, FluidConstants.BOTTLE * 2 / 3)
          .output(MyFluids.POPPY_RUSSIAN_SOUP, FluidConstants.BOTTLE * 2)
          .duration(20 * 15)
          .requiresHeat(HeatCondition.HEATED)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMixing, MyIdentifiers.EGG_DOUGH)
          .require(AllItems.DOUGH.get())
          .require(Items.SUGAR)
          .require(MyFluids.EGG_LIQUID, FluidConstants.BOTTLE)
          .require(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 2)
          .output(MyItems.EGG_DOUGH)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, MyIdentifiers.CRUSHED_ICE)
          .require(Items.ICE)
          .require(Items.PACKED_ICE)
          .require(Items.BLUE_ICE)
          .require(Items.SNOWBALL)
          .require(AllFluids.HONEY.get(), FluidConstants.BOTTLE)
          .output(MyItems.CRUSHED_ICE, 3)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.WHEAT_BLACK_TEA)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.WHEAT_SEEDS)
          .require(Items.BLACK_DYE)
          .require(MyItems.VANILLA)
          .require(Fluids.WATER, FluidConstants.BOTTLE)
          .output(MyItems.WHEAT_BLACK_TEA)
          .averageProcessingDuration()
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, MyIdentifiers.MELON_JUICE)
          .require(Items.MELON_SLICE)
          .require(Items.MELON_SLICE)
          .require(Items.MELON_SLICE)
          .require(Items.MELON_SLICE)
          .require(Items.SUGAR)
          .averageProcessingDuration()
          .output(MyFluids.MELON_JUICE, FluidConstants.BOTTLE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, MyIdentifiers.ICED_MELON_JUICE)
          .require(MyFluids.MELON_JUICE, FluidConstants.BOTTLE)
          .require(ItemsRegistry.MELON_POPSICLE.get())
          .require(MyItems.CRUSHED_ICE)
          .require(Items.NETHER_WART)
          .averageProcessingDuration()
          .output(MyFluids.ICED_MELON_JUICE, FluidConstants.BUCKET / 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMincingRecipe, MyIdentifiers.THICK_HOT_COCOA)
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .require(MyItems.VANILLA)
          .require(AllItems.CHOCOLATE_BERRIES.get())
          .require(AllItems.BAR_OF_CHOCOLATE.get())
          .requiresHeat(HeatCondition.HEATED)
          .averageProcessingDuration()
          .output(MyFluids.THICK_HOT_COCOA, FluidConstants.BOTTLE * 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.SALT)
          .require(Fluids.WATER, FluidConstants.BUCKET)
          .requiresHeat(HeatCondition.HEATED)
          .duration(20 * 15)
          .output(MyItems.SALT)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingCompacting, MyIdentifiers.JELLY_BEANS)
          .require(Items.SUGAR)
          .require(Items.LIGHT_BLUE_DYE)
          .require(Items.PINK_DYE)
          .require(Items.YELLOW_DYE)
          .averageProcessingDuration()
          .output(MyItems.JELLY_BEANS)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.YEAST)
          .require(Items.SUGAR)
          .require(AllItems.DOUGH.get())
          .require(MyItems.KELP_ASH)
          .duration(20 * 60)
          .output(MyItems.YEAST, 5)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingMixing, MyIdentifiers.PASTE)
          .require(MyItems.KELP_ASH)
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(AllItems.WHEAT_FLOUR.get())
          .require(Items.SUGAR)
          .require(Items.SUGAR)
          .require(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE)
          .require(Milk.STILL_MILK, FluidConstants.BOTTLE)
          .require(MyFluids.EGG_LIQUID, FluidConstants.BOTTLE)
          .averageProcessingDuration()
          .output(MyFluids.PASTE, FluidConstants.BUCKET)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingPressureCooking, MyIdentifiers.CAKE_BASE)
          .require(MyFluids.PASTE, FluidConstants.BUCKET / 2)
          .requiresHeat(HeatCondition.HEATED)
          .duration(20 * 10)
          .output(MyItems.CAKE_BASE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(newingCutting, MyIdentifiers.CAKE_BASE_SLICE)
          .require(MyItems.CAKE_BASE)
          .duration(20)
          .output(MyItems.CAKE_BASE_SLICE, 3)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(MyIdentifiers.JELLY_BEANS_CAKE)
          .require(MyItems.JELLY_BEANS)
          .transitionTo(MyItems.JELLY_BEANS)
          .addStep(newingDeploying, LambdasC.addingStep(MyItems.CAKE_BASE_SLICE))
          .addStep(newingDeploying, LambdasC.addingStep(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(newingFilling, LambdasC.addingStep(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(newingDeploying, LambdasC.addingStep(MyItems.JELLY_BEANS))
          .loops(3)
          .addOutput(MyItems.JELLY_BEANS_CAKE, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(MyIdentifiers.SWEET_BERRIES_CAKE)
          .require(MyItems.CAKE_BASE_SLICE)
          .transitionTo(MyItems.CAKE_BASE_SLICE)
          .addStep(newingFilling, LambdasC.addingStep(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(newingDeploying, LambdasC.addingStep(Items.SWEET_BERRIES))
          .loops(1)
          .addOutput(MyItems.SWEET_BERRIES_CAKE, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(MyIdentifiers.RAW_BASQUE_CAKE)
          .require(MyItems.CAKE_BASE)
          .transitionTo(MyItems.CAKE_BASE)
          .addStep(newingDeploying, LambdasC.addingStep(Items.SWEET_BERRIES))
          .addStep(newingDeploying, LambdasC.addingStep(Items.SUGAR))
          .addStep(newingFilling, LambdasC.addingStep(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
          .addStep(newingFilling, LambdasC.addingStep(AllFluids.TEA.get(), FluidConstants.BOTTLE))
          .loops(3)
          .addOutput(MyItems.RAW_BASQUE_CAKE, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(MyIdentifiers.SWEET_BERRIES_CAKE_S)
          .require(Items.SWEET_BERRIES)
          .transitionTo(Items.SWEET_BERRIES)
          .addStep(newingDeploying, LambdasC.addingStep(MyItems.SWEET_BERRIES_CAKE))
          .addStep(newingDeploying, LambdasC.addingStep(Items.SWEET_BERRIES))
          .addStep(newingFilling, LambdasC.addingStep(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
          .addStep(newingDeploying, LambdasC.addingStep(Items.RED_DYE))
          .loops(3)
          .addOutput(MyItems.SWEET_BERRIES_CAKE_S, 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(MyIdentifiers.BROWNIE)
          .require(AllItems.BAR_OF_CHOCOLATE.get())
          .transitionTo(AllItems.BAR_OF_CHOCOLATE.get())
          .addStep(newingDeploying, LambdasC.addingStep(MyItems.CAKE_BASE_SLICE))
          .addStep(newingFilling, LambdasC.addingStep(Milk.STILL_MILK, FluidConstants.BOTTLE))
          .addStep(newingFilling, LambdasC.addingStep(AllFluids.CHOCOLATE.get(), FluidConstants.BOTTLE))
          .addStep(PressingRecipe::new, UnaryOperator.identity())
          .addStep(newingDeploying, LambdasC.addingStep(AllItems.BAR_OF_CHOCOLATE.get()))
          .addStep(newingCutting, UnaryOperator.identity())
          .loops(3)
          .addOutput(new ItemStack(MyItems.BROWNIE, 4), 1)
          .build(exporter);
        new SequencedAssemblyRecipeBuilder(MyIdentifiers.APPLE_CREAM_CAKE)
          .require(Items.APPLE)
          .transitionTo(Items.APPLE)
          .addStep(newingDeploying, LambdasC.addingStep(MyItems.CAKE_BASE_SLICE))
          .addStep(newingFilling, LambdasC.addingStep(Milk.STILL_MILK, FluidConstants.BUCKET / 2))
          .addStep(newingDeploying, LambdasC.addingStep(Items.APPLE))
          .addStep(newingCutting, UnaryOperator.identity())
          .loops(3)
          .addOutput(new ItemStack(MyItems.APPLE_CREAM_CAKE, 1), 1)
          .build(exporter);
    }
}
