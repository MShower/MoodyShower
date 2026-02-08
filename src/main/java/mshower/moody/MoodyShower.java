package mshower.moody;

import mshower.moody.config.Config;
import mshower.moody.config.JsonItemGroupModifierConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoodyShower implements ModInitializer {
    public static final String MOD_ID = "moody-shower";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Config config = Config.read();

    @Override
    public void onInitialize() {
        JsonItemGroupModifierConfig.init();
        LOGGER.info("Moody Shower on");

    }
}