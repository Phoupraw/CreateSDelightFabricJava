package phoupraw.mcmod.createsdelight.misc;

import com.simibubi.create.content.contraptions.behaviour.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.util.math.Vec3d;
@Deprecated
public class BrassTankMovementBehaviour implements MovementBehaviour {
    @Override
    public void onSpeedChanged(MovementContext context, Vec3d oldMotion, Vec3d motion) {
        MovementBehaviour.super.onSpeedChanged(context, oldMotion, motion);
    }
}
