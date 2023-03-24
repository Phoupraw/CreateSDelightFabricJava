import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
public class LambdaTest01 {
    public static void main(String[] args) {
        ProcessingRecipeBuilder.ProcessingRecipeFactory<?> f1 = CuttingRecipe::new;
        ProcessingRecipeBuilder.ProcessingRecipeFactory<?> f2 = CuttingRecipe::new;

        //noinspection ConstantConditions
        System.out.println(f1 == f2);//false

    }
}
