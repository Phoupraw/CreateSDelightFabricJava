package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.AllBlocks;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.display.DynamicDisplayGenerator;
import me.shedaniel.rei.api.client.view.ViewSearchBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.recipe.*;
import phoupraw.mcmod.createsdelight.rei.*;

import java.util.List;
import java.util.Optional;
@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public final class MyREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        EntryStack<ItemStack> depot = EntryStacks.of(AllBlocks.DEPOT.get()), belt = EntryStacks.of(AllBlocks.BELT.get()), basin = EntryStacks.of(AllBlocks.BASIN.get());
        registry.add(PanFryingCategory.INSTANCE);
        registry.addWorkstations(PanFryingCategory.ID, EntryStacks.of(MyItems.PAN));
        registry.add(GrillingCategory.INSTANCE);
        registry.addWorkstations(GrillingCategory.ID, EntryStacks.of(MyItems.GRILL), EntryStacks.of(MyItems.SMART_DRAIN));
        registry.add(SprinklingCategory.INSTANCE);
        registry.addWorkstations(SprinklingCategory.ID, EntryStacks.of(MyItems.SPRINKLER), depot, belt);
        registry.add(SteamingCategory.INSTANCE);
        registry.addWorkstations(SteamingCategory.ID, EntryStacks.of(MyItems.BAMBOO_STEAMER));
        registry.addWorkstations(CategoryIdentifier.of("create", "draining"), EntryStacks.of(MyItems.SMART_DRAIN));
        registry.addWorkstations(VerticalCuttingCategory.ID, EntryStacks.of(MyItems.VERTICAL_CUTTER), depot, belt);
        registry.add(VerticalCuttingCategory.INSTANCE);
        registry.addWorkstations(PressureCookingCategory.ID, EntryStacks.of(MyItems.PRESSURE_COOKER), basin);
        registry.add(PressureCookingCategory.INSTANCE);
        registry.addWorkstations(MincingCategory.ID, EntryStacks.of(MyItems.MINCER), basin);
        registry.add(MincingCategory.INSTANCE);
        registry.add(new LootTableCategory());
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(PanFryingRecipe.class, MyRecipeTypes.PAN_FRYING.getRecipeType(), PanFryingDisplay::new);
        registry.registerRecipeFiller(GrillingRecipe.class, MyRecipeTypes.GRILLING.getRecipeType(), GrillingDisplay::new);
        registry.registerRecipeFiller(SprinklingRecipe.class, MyRecipeTypes.SPRINKLING.getRecipeType(), SprinklingDisplay::new);
        registry.registerRecipeFiller(SteamingRecipe.class, MyRecipeTypes.STEAMING.getRecipeType(), SteamingDisplay::new);
        registry.registerRecipeFiller(VerticalCuttingRecipe.class, MyRecipeTypes.VERTICAL_CUTTING.getRecipeType(), VerticalCuttingDisplay::new);
        registry.registerRecipeFiller(PressureCookingRecipe.class, MyRecipeTypes.PRESSURE_COOKING.getRecipeType(), PressureCookingDisplay::new);
        registry.registerRecipeFiller(MincingRecipe.class, MyRecipeTypes.MINCING.getRecipeType(), MincingDisplay::new);
//        registry.registerFiller(LootTable);
        registry.registerDisplayGenerator(LootTableCategory.ID, new DynamicDisplayGenerator<>() {
            @Override
            public Optional<List<LootTableDisplay>> getRecipeFor(EntryStack<?> entry) {
                return DynamicDisplayGenerator.super.getRecipeFor(entry);
            }

            @Override
            public Optional<List<LootTableDisplay>> getUsageFor(EntryStack<?> entry) {
                return DynamicDisplayGenerator.super.getUsageFor(entry);
            }

            @Override
            public Optional<List<LootTableDisplay>> generate(ViewSearchBuilder builder) {

                return DynamicDisplayGenerator.super.generate(builder);
            }
        });
    }
}
