package mshower.moody.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import static mshower.moody.config.Config.DEFAULT;

import static mshower.moody.MoodyShower.config;

public class ConfigScreenFactoryImpl implements ConfigScreenFactory<Screen> {
    @Override
    public Screen create(Screen screen) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle(Text.translatable("moodyShower.config.title"));
        ConfigCategory category = builder.getOrCreateCategory(Text.empty());
        ConfigEntries entries = new ConfigEntries(builder.entryBuilder(), category);
        builder.setSavingRunnable(() -> {
            config = entries.createConfig();
            config.write();
        });
        return builder.build();
    }

    private static Text fieldName(String id) {
        return Text.translatable("moodyShower.config.entry." + id);
    }

    private static class ConfigEntries {

        private final ConfigEntryBuilder builder;
        private final ConfigCategory category;
        private final BooleanListEntry forceRenderChristmasChest, toggleChristmasChestRendering;

        public ConfigEntries(ConfigEntryBuilder builder, ConfigCategory category) {
            this.builder = builder;
            this.category = category;

            forceRenderChristmasChest = createBoolField("forceControlChristmasChestRendering", config.forceControlChristmasChestRendering, DEFAULT.forceControlChristmasChestRendering);
            toggleChristmasChestRendering = createBoolField("toggleChristmasChestRendering", config.toggleChristmasChestRendering, DEFAULT.toggleChristmasChestRendering);
        }

        private BooleanListEntry createBoolField(String id, boolean value, boolean defaultValue) {
            BooleanListEntry entry = builder.startBooleanToggle(fieldName(id), value)
                    .setDefaultValue(defaultValue)
                    .build();
            category.addEntry(entry);
            return entry;
        }


        public Config createConfig() {
            return new Config(
                    forceRenderChristmasChest.getValue(),
                    toggleChristmasChestRendering.getValue()
            );
        }
    }
}
