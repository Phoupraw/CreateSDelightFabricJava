package phoupraw.mcmod.createsdelight;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;

public final class CreateSDelight {
    public static final String MOD_ID = "createsdelight";
    @ApiStatus.Internal
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    //    public static Text mst()
    static {
        CreateSDelight.LOGGER.atLevel(Level.ALL);
    }
    private CreateSDelight() {}
}
