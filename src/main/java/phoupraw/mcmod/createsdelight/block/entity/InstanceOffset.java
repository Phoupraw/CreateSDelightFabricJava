package phoupraw.mcmod.createsdelight.block.entity;

import com.jozufozu.flywheel.util.AnimationTickHolder;
import org.jetbrains.annotations.Contract;
public interface InstanceOffset {
    /**
     * 负为向下，正为向上。
     *
     * @param partialTicks {@link AnimationTickHolder#getPartialTicks()}
     * @return 以米为单位
     */
    @Contract(pure = true)
    double getOffset(float partialTicks);
}
