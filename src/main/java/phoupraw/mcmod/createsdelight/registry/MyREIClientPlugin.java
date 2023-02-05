package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.rei.CreateREI;
import me.shedaniel.rei.api.client.plugins.*;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SteamingRecipe;
import phoupraw.mcmod.createsdelight.rei.*;
@Environment(EnvType.CLIENT)
public final class MyREIClientPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(PanFryingCategory.INSTANCE);
        registry.addWorkstations(PanFryingCategory.ID, EntryStacks.of(MyItems.PAN));
        registry.add(GrillingCategory.INSTANCE);
        registry.addWorkstations(GrillingCategory.ID, EntryStacks.of(MyItems.GRILL),EntryStacks.of(MyItems.SMART_DRAIN));
        registry.add(SprinklingCategory.INSTANCE);
        registry.addWorkstations(SprinklingCategory.ID, EntryStacks.of(MyItems.SPRINKLER));
        registry.add(SteamingCategory.INSTANCE);
        registry.addWorkstations(SteamingCategory.ID, EntryStacks.of(MyItems.BAMBOO_STEAMER),EntryStacks.of(AllBlocks.BASIN.get()));
        registry.addWorkstations(CategoryIdentifier.of("create","draining"),EntryStacks.of(MyItems.SMART_DRAIN));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(PanFryingRecipe.class, MyRecipeTypes.PAN_FRYING.getRecipeType(), PanFryingDisplay::new);
        registry.registerRecipeFiller(GrillingRecipe.class, MyRecipeTypes.GRILLING.getRecipeType(), GrillingDisplay::new);
        registry.registerRecipeFiller(SprinklingRecipe.class, MyRecipeTypes.SPRINKLING.getRecipeType(), SprinklingDisplay::new);
        registry.registerRecipeFiller(SteamingRecipe.class, MyRecipeTypes.STEAMING.getRecipeType(), SteamingDisplay::new);
    }
}
