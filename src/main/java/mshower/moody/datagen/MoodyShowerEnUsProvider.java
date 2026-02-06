package mshower.moody.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class MoodyShowerEnUsProvider extends FabricLanguageProvider {
    private static final String languageCode = "en_us";

    public MoodyShowerEnUsProvider(FabricDataOutput dataOutput) {
        super(dataOutput, languageCode);
    }

    private static String fieldName(String id) {
        return "moodyShower.config.entry." + id;
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("moodyShower.config.title", "Moody Shower Config");
        translationBuilder.add(fieldName("forceControlChristmasChestRendering"), "Force Control Christmas Chest Rendering");
        translationBuilder.add(fieldName("toggleChristmasChestRendering"), "Toggle Christmas Chest Rendering");
    }
}
