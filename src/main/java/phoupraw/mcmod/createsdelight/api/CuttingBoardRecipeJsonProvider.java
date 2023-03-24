package phoupraw.mcmod.createsdelight.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.ingredient.ChanceResult;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
public class CuttingBoardRecipeJsonProvider implements RecipeJsonProvider {
    private final CuttingBoardRecipe recipe;

    public CuttingBoardRecipeJsonProvider(CuttingBoardRecipe recipe) {this.recipe = recipe;}

    @Override
    public void serialize(JsonObject json) {
        json.addProperty("group", recipe.getGroup());
        var ingredients = new JsonArray(1);
        ingredients.add(recipe.getIngredients().get(0).toJson());
        json.add("ingredients", ingredients);
        json.add("tool", recipe.getTool().toJson());
        var results = new JsonArray(1);
        for (ChanceResult result : recipe.getRollableResults()) results.add(result.serialize());
        json.add("result", results);
        json.addProperty("sound", recipe.getSoundEvent());
    }

    @Override
    public Identifier getRecipeId() {
        return new Identifier(recipe.getId().getNamespace(), "cutting_board/" + recipe.getId().getPath());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return recipe.getSerializer();
    }

    @Nullable
    @Override
    public JsonObject toAdvancementJson() {
        return null;
    }

    @Nullable
    @Override
    public Identifier getAdvancementId() {
        return null;
    }
}
