package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class PressureCookingCategory extends BasinCategory<PressureCookingDisplay> {
    public static final CategoryIdentifier<PressureCookingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.PRESSURE_COOKING.getId());
    public static final PressureCookingCategory INSTANCE = new PressureCookingCategory();

    @Override
    public CategoryIdentifier<? extends PressureCookingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.PRESSURE_COOKER);
    }

}
