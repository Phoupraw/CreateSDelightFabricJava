package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.ingredient.ChanceResult;
import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllFluids;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import phoupraw.mcmod.createsdelight.api.CuttingBoardRecipeJsonProvider;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyFluids;
import phoupraw.mcmod.createsdelight.registry.MyIdentifiers;
import phoupraw.mcmod.createsdelight.registry.MyItemTags;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import java.util.function.Consumer;
public class MyRecipeProvider extends FabricRecipeProvider {
    public MyRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        offerStonecuttingRecipe(exporter, MyItems.THICK_PORK_SLICE, Items.PORKCHOP, 2);
        offerStonecuttingRecipe(exporter, MyItems.THIN_PORK_SLICE, MyItems.THICK_PORK_SLICE, 2);
        ShapedRecipeJsonBuilder.create(MyItems.SPRINKLER)
          .pattern("A")
          .pattern("B")
          .pattern("C")
          .input('A', AllBlocks.SHAFT.get())
          .input('B', AllBlocks.ANDESITE_CASING.get())
          .input('C', Items.IRON_BARS)
          .criterion("stupidMojang", conditionsFromItem(AllBlocks.SHAFT.get()))
          .offerTo(exporter);

//        exporter.accept(new CuttingBoardRecipeJsonProvider(new CuttingBoardRecipe(MyIdentifiers.THICK_PORK_SLICE, "", Ingredient.ofItems(Items.PORKCHOP), Ingredient.fromTag(TagsRegistry.STRAW_HARVESTERS), DefaultedList.copyOf(ChanceResult.EMPTY, new ChanceResult(new ItemStack(MyItems.THICK_PORK_SLICE, 2), 1)), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.name())));
//        exporter.accept(new CuttingBoardRecipeJsonProvider(new CuttingBoardRecipe(MyIdentifiers.THIN_PORK_SLICE, "", Ingredient.ofItems(MyItems.THICK_PORK_SLICE), Ingredient.fromTag(TagsRegistry.STRAW_HARVESTERS), DefaultedList.copyOf(ChanceResult.EMPTY, new ChanceResult(new ItemStack(MyItems.THIN_PORK_SLICE, 2), 1)), SoundsRegistry.BLOCK_CUTTING_BOARD_KNIFE.name())));

        new ProcessingRecipeBuilder<>(FillingRecipe::new, new Identifier(MyIdentifiers.MOD_ID, "chocolate_pie"))
          .require(ItemsRegistry.PIE_CRUST.get())
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET / 2)
          .output(ItemsRegistry.CHOCOLATE_PIE.get())
          .build(exporter);
        new ProcessingRecipeBuilder<>(CuttingRecipe::new, MyIdentifiers.PAN)
          .require(ItemsRegistry.SKILLET.get())
          .output(MyItems.PAN)
          .output(Items.BRICK)
          .duration(40)
          .build(exporter);
        new ProcessingRecipeBuilder<>(PanFryingRecipe::new, MyIdentifiers.PAN_FRIED_BEEF_PATTY)
          .require(ItemsRegistry.MINCED_BEEF.get())
          .require(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 10)
          .output(MyItems.PAN_FRIED_BEEF_PATTY)
          .duration(100)
          .build(exporter);
        new ProcessingRecipeBuilder<>(CompactingRecipe::new, MyIdentifiers.SUNFLOWER_OIL)
          .require(Items.SUNFLOWER)
          .output(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 2)
          .build(exporter);
        new ProcessingRecipeBuilder<>(CuttingRecipe::new, MyIdentifiers.GRILL)
          .require(ItemsRegistry.STOVE.get())
          .output(MyItems.GRILL)
          .output(Items.BRICKS, 4)
          .output(Items.CAMPFIRE)
          .duration(40)
          .build(exporter);
        new ProcessingRecipeBuilder<>(PanFryingRecipe::new, MyIdentifiers.PAN_FRIED_PORK_SLICE)
          .require(MyItems.THICK_PORK_SLICE)
          .output(MyItems.PAN_FRIED_PORK_SLICE)
          .duration(140)
          .build(exporter);
        new ProcessingRecipeBuilder<>(GrillingRecipe::new, MyIdentifiers.GRILLED_PORK_SLICE)
          .require(MyItems.THIN_PORK_SLICE)
          .output(MyItems.GRILLED_PORK_SLICE)
          .duration(60)
          .build(exporter);
        new ProcessingRecipeBuilder<>(SprinklingRecipe::new, MyIdentifiers.SUGAR_PORK)
          .require(MyItemTags.COOKED_PORK)
          .require(Items.SUGAR)
          .output(MyItems.SUGAR_PORK)
          .build(exporter);
        new ProcessingRecipeBuilder<>(SprinklingRecipe::new, MyIdentifiers.LEAVES_RICE)
          .require(ItemsRegistry.COOKED_RICE.get())
          .require(ItemTags.LEAVES)
          .output(MyItems.LEAVES_RICE)
          .build(exporter);
        new ProcessingRecipeBuilder<>(PanFryingRecipe::new, MyIdentifiers.VANILLA)
          .require(Items.GRASS)
          .require(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 10)
          .output(MyItems.VANILLA)
          .duration(60)
          .build(exporter);
    }
}
