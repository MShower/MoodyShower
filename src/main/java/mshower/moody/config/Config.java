package mshower.moody.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;

import static mshower.moody.MoodyShower.MOD_ID;
import static mshower.moody.MoodyShower.LOGGER;

public class Config {
    private static final File CONFIG_FILE =
            FabricLoader.getInstance()
                    .getConfigDir()
                    .resolve(MOD_ID)
                    .resolve(MOD_ID + ".json")
                    .toFile();

    public final boolean forceControlChristmasChestRendering;
    public final boolean toggleChristmasChestRendering;
    public final boolean toggleJsonCustomItemGroups;

    public static final Config DEFAULT = new Config(false, false, false);

    public Config(boolean forceControlRenderChristmasChest, boolean toggleChristmasChestRendering, boolean toggleJsonCustomItemGroups) {
        this.forceControlChristmasChestRendering = forceControlRenderChristmasChest;
        this.toggleChristmasChestRendering = toggleChristmasChestRendering;
        this.toggleJsonCustomItemGroups = toggleJsonCustomItemGroups;
    }

    public static Config read() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonElement element = JsonParser.parseReader(reader);
            if (!element.isJsonObject()) return DEFAULT;

            JsonObject object = element.getAsJsonObject();
            return new Config(
                    readBool(object, "forceControlChristmasChestRendering", DEFAULT.forceControlChristmasChestRendering),
                    readBool(object, "toggleChristmasChestRendering", DEFAULT.toggleChristmasChestRendering),
                    readBool(object, "toggleJsonCustomItemGroups", DEFAULT.toggleJsonCustomItemGroups)
            );
        }
        catch (FileNotFoundException e) {
            return DEFAULT;
        }
        catch (IOException | JsonIOException e) {
            LOGGER.error("Couldn't read {}, using default settings instead", CONFIG_FILE, e);
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
            LOGGER.warn("Invalid boolean '{}' for option '{}'", el, key);
            return fallback;

        } catch (ClassCastException | IllegalStateException e) {
            LOGGER.warn("Exception reading boolean '{}' for option '{}'", el, key, e);
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
                    .name("toggleJsonCustomItemGroups").value(toggleJsonCustomItemGroups)
                    .endObject();
        }
        catch (IOException e) {
            LOGGER.error("Couldn't write settings to {}", CONFIG_FILE, e);
        }
    }
}
