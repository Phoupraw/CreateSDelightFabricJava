package phoupraw.mcmod.createsdelight.registry;

import me.shedaniel.rei.api.client.plugins.*;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.rei.GrillingCategory;
import phoupraw.mcmod.createsdelight.rei.GrillingDisplay;
import phoupraw.mcmod.createsdelight.rei.PanFryingCategory;
import phoupraw.mcmod.createsdelight.rei.PanFryingDisplay;
@Environment(EnvType.CLIENT)
public final class MyREIClientPlugin implements REIClientPlugin {
//    public static final CategoryIdentifier<MyDisplay> CATEGORY_IDENTIFIER = CategoryIdentifier.of(Objects.requireNonNull(Registry.RECIPE_TYPE.getId(MyRecipeTypes.PAN_FRYING.getRecipeType())));

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(PanFryingCategory.INSTANCE);
        registry.addWorkstations(PanFryingCategory.ID, EntryStacks.of(MyItems.PAN));
        registry.add(GrillingCategory.INSTANCE);
        registry.addWorkstations(GrillingCategory.ID, EntryStacks.of(MyItems.GRILL));
//        var id = Objects.requireNonNull(Registry.RECIPE_TYPE.getId(MyRecipeTypes.PAN_FRYING.getRecipeType()));
//        registry.add(new PanFryingCategory(new CreateRecipeCategory.Info<>(
//          CategoryIdentifier.of(id),
//          Text.translatable("category."+id.toString().replace(':','.')),
//
//        )));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(PanFryingRecipe.class, MyRecipeTypes.PAN_FRYING.getRecipeType(), PanFryingDisplay::new);
        registry.registerRecipeFiller(GrillingRecipe.class, MyRecipeTypes.GRILLING.getRecipeType(), GrillingDisplay::new);
    }
}
