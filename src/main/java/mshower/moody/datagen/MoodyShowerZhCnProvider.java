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
        translationBuilder.add(fieldName("toggleChristmasChestRendering"), "强制切换圣诞箱子显示");
        translationBuilder.add(fieldName("toggleJsonCustomItemGroups"), "启用 JSON 自定义物品标签页（需要重启Minecraft）");
        translationBuilder.add(fieldName("toggleBedUseInTheEnd"), "禁止在末地使用床");
        translationBuilder.add(fieldName("toggleBedUseInTheNether"), "禁止在下界使用床");
    }
}
