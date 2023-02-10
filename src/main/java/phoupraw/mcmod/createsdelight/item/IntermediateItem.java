package phoupraw.mcmod.createsdelight.item;

import io.github.tropheusj.serialization_hooks.ingredient.BaseCustomIngredient;
import io.github.tropheusj.serialization_hooks.ingredient.IngredientDeserializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;
//有序合成、无序合成、烧炼、高炉、烟熏、营火、切石、锻造
//砧板、厨锅
//转化、粉碎、切割、碾磨、工作盆、混合搅拌、压块、冲压、砂纸、水洗、缠魂、装配、注液、分液、手动装配、动力合成、序列装配、工具盒染色

//
//砧板、厨锅
//切割、工作盆、混合搅拌、压块、冲压、装配、注液、分液、动力合成
public class IntermediateItem extends Item {
    public static @Nullable Recipe<?> getNextRecipe(NbtCompound root, RecipeManager manager) {
        if (!root.contains("nextRecipe", NbtElement.STRING_TYPE)) return null;
        var id = new Identifier(root.getString("nextRecipe"));
        return manager.get(id).orElse(null);
    }

    public static void putNextRecipe(NbtCompound root, @Nullable Recipe<?> recipe) {
        if (recipe == null) {
            root.remove("nextRecipe");
            return;
        }
        root.putString("nextRecipe", recipe.getId().toString());
    }

    public IntermediateItem(Settings settings) {
        super(settings);
    }

}
