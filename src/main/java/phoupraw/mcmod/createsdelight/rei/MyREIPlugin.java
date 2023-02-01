package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.plugins.*;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
public final class MyREIPlugin implements REIClientPlugin {
//    public static final CategoryIdentifier<MyDisplay> CATEGORY_IDENTIFIER = CategoryIdentifier.of(Objects.requireNonNull(Registry.RECIPE_TYPE.getId(MyRecipeTypes.PAN_FRYING.getRecipeType())));

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(PanFryingCategory.INSTANCE);
        registry.addWorkstations(PanFryingCategory.ID, EntryStacks.of(MyItems.PAN));
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
    }
}
