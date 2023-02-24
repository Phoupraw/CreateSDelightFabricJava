package phoupraw.mcmod.createsdelight.api;

import phoupraw.mcmod.common.misc.Stage;
public class ProcessingTimer {
    public int elapsed;
    public int target;

    public void tick() {
        if(elapsed<target) elapsed++;
    }

    public Stage getStage() {
        if (elapsed == -1) return Stage.NOT_DOING;
        if (elapsed >= target) return Stage.DONE;
        return Stage.DOING;
    }

}
