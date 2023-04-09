package phoupraw.mcmod.createsdelight.exp;

import net.minecraft.util.Identifier;
public abstract class BasicRecipe implements ShabbyRecipe {
    private final Identifier id;

    protected BasicRecipe(Identifier id) {
        this.id = id;
    }

    @Override
    public Identifier getId() {
        return id;
    }
}
