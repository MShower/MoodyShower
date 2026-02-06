package mshower.moody.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class MoodyShowerZhCnProvider extends FabricLanguageProvider {
    private static final String languageCode = "zh_cn";

    public MoodyShowerZhCnProvider(FabricDataOutput dataOutput) {
        super(dataOutput, languageCode);
    }

    private static String fieldName(String id) {
        return "moodyShower.config.entry." + id;
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add("moodyShower.config.title", "Moody Shower 配置");
        translationBuilder.add(fieldName("forceControlChristmasChestRendering"), "强制控制圣诞箱子渲染逻辑");
        translationBuilder.add(fieldName("toggleChristmasChestRendering"), "切换圣诞箱子显示");
    }
}
