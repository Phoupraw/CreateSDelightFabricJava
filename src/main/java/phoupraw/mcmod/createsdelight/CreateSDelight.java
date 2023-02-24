package phoupraw.mcmod.createsdelight;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//TODO 冲压（分液池网格）、慢速搅拌
public final class CreateSDelight {
    public static final String MOD_ID = "createsdelight";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    static {
        CreateSDelight.LOGGER.atLevel(Level.ALL);
    }
    private CreateSDelight() {}
}
