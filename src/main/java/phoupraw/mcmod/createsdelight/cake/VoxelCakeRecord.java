package phoupraw.mcmod.createsdelight.cake;

import com.google.common.collect.Multimap;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Vec3i;

public class VoxelCakeRecord implements VoxelCake {

    private final Multimap<CakeIngredient, BlockBox> content;
    private final Vec3i size;

    public VoxelCakeRecord(Multimap<CakeIngredient, BlockBox> content, Vec3i size) {
        this.content = content;
        this.size = size;
    }

    @Override
    public Multimap<CakeIngredient, BlockBox> getContent() {
        return content;
    }

    @Override
    public Vec3i getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "PredefinedCakeRecord{" +
               "content=" + content +
               ", size=" + size +
               '}';
    }

}
