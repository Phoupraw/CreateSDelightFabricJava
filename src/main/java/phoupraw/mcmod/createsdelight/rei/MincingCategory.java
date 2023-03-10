package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class MincingCategory extends BasinCategory<MincingDisplay> {
    public static final CategoryIdentifier<MincingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.MINCING.getId());
    public static final MincingCategory INSTANCE = new MincingCategory();

    @Override
    public CategoryIdentifier<? extends MincingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.MINCER);
    }

}
