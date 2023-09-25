package phoupraw.mcmod.createsdelight.cake;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

public interface CakeIngredient {
    BlockApiLookup<CakeIngredient, @Nullable Void> LOOKUP = BlockApiLookup.get(CSDIdentifiers.CAKE_INGREDIENT, CakeIngredient.class, Void.class);
    CakeIngredient EMPTY = new CakeIngredientRecord(0, CSDIdentifiers.EMPTY);
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull CakeIngredient of(double hungerPerBucket, Identifier textureId) {
        return new CakeIngredientRecord(hungerPerBucket, textureId);
    }
    double getHungerPerBucket();
    Identifier getTextureId();
}
