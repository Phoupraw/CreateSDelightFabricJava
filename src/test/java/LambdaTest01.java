import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;

public class LambdaTest01 {
    public static void main(String[] args) {
       ProcessingRecipeBuilder.ProcessingRecipeFactory<?> f1 = CuttingRecipe::new;
       ProcessingRecipeBuilder.ProcessingRecipeFactory<?> f2 = CuttingRecipe::new;

        //noinspection ConstantConditions
        System.out.println(f1 == f2);//false

    }
}
