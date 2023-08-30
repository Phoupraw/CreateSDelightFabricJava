package phoupraw.mcmod.createsdelight.cake;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface CakeIngredient {

    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull CakeIngredient of(double hungerPerBucket, Identifier textureId) {
        return new CakeIngredientRecord(hungerPerBucket, textureId);
    }

    double getHungerPerBucket();
    Identifier getTextureId();

}
