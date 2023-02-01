package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.contraptions.components.mixer.CompactingRecipe;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyFluids;
import phoupraw.mcmod.createsdelight.registry.MyIdentifiers;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import java.util.function.Consumer;
public class MyRecipeProvider extends FabricRecipeProvider {
    public MyRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        new ProcessingRecipeBuilder<>(FillingRecipe::new, new Identifier(CreateSDelight.MOD_ID, "chocolate_pie"))
          .require(ItemsRegistry.PIE_CRUST.get())
          .require(AllFluids.CHOCOLATE.get(), FluidConstants.BUCKET / 2)
          .output(ItemsRegistry.CHOCOLATE_PIE.get())
          .build(exporter);
        new ProcessingRecipeBuilder<>(CuttingRecipe::new, MyIdentifiers.PAN)
          .require(ItemsRegistry.SKILLET.get())
          .output(MyItems.PAN)
          .output(Items.BRICK)
          .duration(20)
          .build(exporter);
        new ProcessingRecipeBuilder<>(PanFryingRecipe::new, new Identifier(CreateSDelight.MOD_ID, "beef"))
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
          .output(Items.BRICKS,4)
          .output(Items.CAMPFIRE)
          .duration(20)
          .build(exporter);
    }
}
