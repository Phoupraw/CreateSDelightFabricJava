package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;
public class PressureCookingCategory extends BasinCategory<PressureCookingDisplay> {
    public static final CategoryIdentifier<PressureCookingDisplay> ID = CategoryIdentifier.of(CDRecipeTypes.PRESSURE_COOKING.getId());
    public static final PressureCookingCategory INSTANCE = new PressureCookingCategory();

    @Override
    public CategoryIdentifier<? extends PressureCookingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CDItems.PRESSURE_COOKER);
    }

}
