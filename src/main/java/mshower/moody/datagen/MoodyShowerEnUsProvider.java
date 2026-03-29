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
        translationBuilder.add(fieldName("toggleChristmasChestRendering"), "Enable Christmas Chest Rendering Override");
        translationBuilder.add(fieldName("toggleJsonCustomItemGroups"), "Enable JSON Custom Item Groups (NEED RESTART MINECRAFT)");
        translationBuilder.add(fieldName("toggleBedUseInTheEnd"), "Disable Bed Interaction in the End");
        translationBuilder.add(fieldName("toggleBedUseInTheNether"), "Disable Bed Interaction in the Nether");
        translationBuilder.add(fieldName("toggleBetterCreativeInventorySearch"), "Enable Better Creative Inventory Search");
    }
}
