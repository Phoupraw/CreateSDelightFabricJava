package phoupraw.mcmod.createsdelight.cake;

import net.minecraft.util.Identifier;

public final class CakeIngredientRecord implements CakeIngredient {

    private final double hungerPerBucket;
    private final Identifier textureId;

    public CakeIngredientRecord(double hungerPerBucket, Identifier textureId) {
        this.hungerPerBucket = hungerPerBucket;
        this.textureId = textureId;
    }

    @Override
    public double getHungerPerBucket() {
        return hungerPerBucket;
    }

    @Override
    public Identifier getTextureId() {
        return textureId;
    }

    @Override
    public String toString() {
        return "CakeIngredientRecord[" +
               "hungerPerBucket=" + hungerPerBucket + ", " +
               "textureId=" + textureId + ']';
    }

}
