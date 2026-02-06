package mshower.moody.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import mshower.moody.MoodyShower;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class Config {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final File CONFIG_FILE =
            FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve(MoodyShower.MOD_ID + ".json")
                    .toFile();

    public final boolean forceControlChristmasChestRendering;
    public final boolean toggleChristmasChestRendering;

    public static final Config DEFAULT = new Config(false, false);

    public Config(boolean forceControlRenderChristmasChest, boolean toggleChristmasChestRendering) {
        this.forceControlChristmasChestRendering = forceControlRenderChristmasChest;
        this.toggleChristmasChestRendering = toggleChristmasChestRendering;
    }

    public static Config read() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonElement element = JsonParser.parseReader(reader);
            if (!element.isJsonObject()) return DEFAULT;

            JsonObject object = element.getAsJsonObject();
            return new Config(
                    readBool(object, "forceControlChristmasChestRendering", DEFAULT.forceControlChristmasChestRendering),
                    readBool(object, "toggleChristmasChestRendering", DEFAULT.toggleChristmasChestRendering)
            );
        }
        catch (FileNotFoundException e) {
            return DEFAULT;
        }
        catch (IOException | JsonIOException e) {
            LOGGER.error(
                    ()->
                    "[Moody Shower] Couldn't read "
                            + CONFIG_FILE
                            + ", using default settings instead",
                    e);
            return DEFAULT;
        }
    }

    private static boolean readBool(JsonObject o, String key, boolean fallback) {
        JsonElement el = o.get(key);
        if (el == null || el.isJsonNull()) return fallback;

        try {
            if (el.isJsonPrimitive() && el.getAsJsonPrimitive().isBoolean()) {
                return el.getAsBoolean();
            }
            LOGGER.warn("[Moody Shower] Invalid boolean '{}' for option '{}'", el, key);
            return fallback;

        } catch (ClassCastException | IllegalStateException e) {
            LOGGER.warn("[Moody Shower] Exception reading boolean '{}' for option '{}'", el, key, e);
            return fallback;
        }
    }
    public void write() {
        try (FileWriter fileWriter = new FileWriter(CONFIG_FILE);
             JsonWriter jsonWriter = new JsonWriter(fileWriter)
        ) {
            jsonWriter.setIndent("  ");
            jsonWriter
                    .beginObject()
                    .name("forceControlChristmasChestRendering").value(forceControlChristmasChestRendering)
                    .name("toggleChristmasChestRendering").value(toggleChristmasChestRendering)
                    .endObject();
        }
        catch (IOException e) {
            LOGGER.error(
                    ()->
                            "[Moody Shower] Couldn't write settings to "
                                    + CONFIG_FILE,
                    e);
        }
    }
}
