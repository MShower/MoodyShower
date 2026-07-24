package mshower.moody.config;

import mshower.moody.utils.JsonItemGroupModifierUtils;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static mshower.moody.MoodyShower.*;

public class JsonItemGroupModifierConfig {

    private static final Path CONFIG_PATH = FabricLoader.getInstance()
            .getConfigDir()
            .resolve(ORG_ID)
            .resolve(MOD_ID)
            .resolve("item-groups");

    public static void init() {
        if (config.toggleJsonCustomItemGroups) {
            try {
                Files.createDirectories(CONFIG_PATH);
            } catch (IOException e) {
                LOGGER.error("Couldn't prepare JSON item group configs in {}", CONFIG_PATH, e);
            }
            try (Stream<Path> stream = Files.list(CONFIG_PATH)) {
                stream.filter(Files::isRegularFile)
                        .filter(p -> p.getFileName().toString().endsWith(".json"))
                        .forEach(JsonItemGroupModifierUtils::load);
            } catch (IOException e) {
                LOGGER.error("Couldn't read JSON item group configs in {}", CONFIG_PATH, e);
            }
        }
    }
}
