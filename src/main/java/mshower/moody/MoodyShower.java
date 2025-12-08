package mshower.moody;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoodyShower implements ModInitializer {
    public static final String MOD_ID = "moody-shower";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        LOGGER.info("MoodyShower Loaded.");
    }
}