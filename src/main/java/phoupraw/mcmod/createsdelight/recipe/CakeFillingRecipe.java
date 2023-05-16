package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.registry.CDFluids;
import phoupraw.mcmod.createsdelight.registry.CDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.ArrayList;
import java.util.List;
public class CakeFillingRecipe extends FillingRecipe {
    @ApiStatus.Internal
    public static final FluidIngredient FLUID_INGREDIENT = FluidIngredient.fromFluid(Fluids.EMPTY, FluidConstants.BUCKET);
    static {
        List<FluidStack> list = new ArrayList<>();
        for (Fluid f : new Fluid[]{Milk.STILL_MILK, AllFluids.CHOCOLATE.get(), AllFluids.TEA.get(), AllFluids.HONEY.get(), CDFluids.PASTE, CDFluids.EGG_LIQUID, CDFluids.TOMATO_SAUCE}) {
            list.add(new FluidStack(f, FluidConstants.BUCKET));
        }
        FLUID_INGREDIENT.matchingFluidStacks = list;
    }
    @ApiStatus.Internal
    public static final CakeFillingRecipe INSTANCE = new ProcessingRecipeBuilder<>(CakeFillingRecipe::new, CDIdentifiers.of("blueprint_cake"))
      .require(CDItems.CAKE_BLUEPRINT)
      .require(FLUID_INGREDIENT)
      .output(CDItems.CAKE_BLUEPRINT)
      .build();

    public static CakeFillingRecipe getInstance() {
        return INSTANCE;
    }

    @ApiStatus.Internal
    public CakeFillingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(params);
    }

    @Override
    public boolean matches(Inventory inv, World p_77569_2_) {
        return false;
    }
}
