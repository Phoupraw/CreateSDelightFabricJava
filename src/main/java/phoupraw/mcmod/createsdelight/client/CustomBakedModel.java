package phoupraw.mcmod.createsdelight.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface CustomBakedModel extends BakedModel {
    @Override
    default List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return List.of();
    }
    @Override
    default boolean isBuiltin() {
        return true;
    }
    @Override
    default boolean isVanillaAdapter() {
        return false;
    }
}
