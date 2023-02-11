package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
public interface ItemEntityHandler {
    ItemVariant getResource();
    void setResource(ItemVariant resource);
    long getAmount();
    void setAmount(long amount);
    Vec3d getPos();
    void setPos(Vec3d pos);

    World getWorld();
    double getHeat();

}
