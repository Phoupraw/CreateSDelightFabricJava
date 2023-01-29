package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllFluids;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.CreateSDelight;

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
    }
}
