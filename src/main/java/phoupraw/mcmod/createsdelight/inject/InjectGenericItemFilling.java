package phoupraw.mcmod.createsdelight.inject;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import phoupraw.mcmod.createsdelight.api.GetWorldContainerItemContext;
public interface InjectGenericItemFilling {
    static void addWorld(Args args, World world) {
        args.set(1, GetWorldContainerItemContext.of(world, args.get(1)));
    }
}
