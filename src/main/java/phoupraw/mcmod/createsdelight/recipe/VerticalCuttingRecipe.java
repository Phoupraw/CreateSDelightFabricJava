package phoupraw.mcmod.createsdelight.recipe;

import com.google.gson.JsonObject;
import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.nhoryzon.mc.farmersdelight.recipe.ingredient.ChanceResult;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.ArrayList;
import java.util.List;
public class VerticalCuttingRecipe extends DeprecatedMatchesRecipe {

    public static VerticalCuttingRecipe of(CuttingBoardRecipe cbr) {
        List<ProcessingOutput> list = new ArrayList<>();
        for (ChanceResult cr : cbr.getRollableResults()) list.add(new ProcessingOutput(cr.stack(), cr.chance()));
        VerticalCuttingRecipe vcr = new ProcessingRecipeBuilder<>(VerticalCuttingRecipe::new, new Identifier(CreateSDelight.MOD_ID, cbr.getId().getPath()))
          .require(cbr.getIngredients().get(0))
          .withItemOutputs(list.toArray(new ProcessingOutput[0]))
          .build();
        vcr.setKnives(2);
        return vcr;
    }

    /**
     * 切多少刀。默认为1。
     */
    private int knives;
    {
        setKnives(1);
    }
    public VerticalCuttingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {this(MyRecipeTypes.VERTICAL_CUTTING, params);}

    public VerticalCuttingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 5;
    }

    @Override
    public void writeAdditional(@NotNull JsonObject json) {
        super.writeAdditional(json);
        json.addProperty("knives", getKnives());
    }

    @Override
    public void writeAdditional(@NotNull PacketByteBuf buffer) {
        super.writeAdditional(buffer);
        buffer.writeInt(getKnives());
    }

    @Override
    public void readAdditional(@NotNull JsonObject json) {
        super.readAdditional(json);
        if (json.has("knives")) {
            setKnives(JsonHelper.getInt(json, "knives"));
        }
    }

    @Override
    public void readAdditional(@NotNull PacketByteBuf buffer) {
        super.readAdditional(buffer);
        setKnives(buffer.readInt());
    }

    public int getKnives() {
        return knives;
    }

    public void setKnives(int knives) {
        this.knives = knives;
//        processingDuration = 5/*刀放下来*/ + (knives - 1) * 5/*反复切*/ + 5/*刀提上去*/;
        processingDuration = (knives + 1) * 6 * 20;
    }

    public static class Builder extends ProcessingRecipeBuilder<VerticalCuttingRecipe> {
        private int knives = 1;

        public Builder(Identifier recipeId) {
            super(VerticalCuttingRecipe::new, recipeId);
        }

        @Contract("_->this")
        public Builder withKnives(int knives) {
            this.knives = knives;
            return this;
        }

        public int getKnives() {
            return knives;
        }

        @Override
        public VerticalCuttingRecipe build() {
            var recipe = super.build();
            recipe.setKnives(getKnives());
            return recipe;
        }
    }
}
