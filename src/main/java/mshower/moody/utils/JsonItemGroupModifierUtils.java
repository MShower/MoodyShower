package mshower.moody.utils;

import com.google.gson.Gson;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mshower.moody.MoodyShower.MOD_ID;
import static mshower.moody.MoodyShower.LOGGER;

public class JsonItemGroupModifierUtils {
    public String name;
    public String icon;
    public List<String> items;

    public static final Map<String, ItemGroup> CUSTOM_GROUPS = new HashMap<>();

    public static void load(Path path) {
        Gson gson = new Gson();

        try (Reader reader = Files.newBufferedReader(path)) {
            JsonItemGroupModifierUtils json = gson.fromJson(reader, JsonItemGroupModifierUtils.class);
            String id = json.name.toLowerCase().replace(" ", "_");
            String iconIdStr = json.icon != null ? json.icon : json.items.get(0);
            Item iconItem = Registries.ITEM.get(new Identifier(iconIdStr));
            RegistryKey<ItemGroup> key = RegistryKey.of(
                    RegistryKeys.ITEM_GROUP,
                    new Identifier(MOD_ID, id)
            );
            ItemGroup groupInstance = Registry.register(Registries.ITEM_GROUP, key,
                    FabricItemGroup.builder()
                            .icon(() -> new ItemStack(iconItem))
                            .displayName(Text.literal(json.name))
                            .entries((context, entries) -> {
                                for (String itemId : json.items) {
                                    Item item = Registries.ITEM.get(new Identifier(itemId));
                                    if (item != Items.AIR) entries.add(new ItemStack(item));
                                }
                            })
                            .build()
            );
            CUSTOM_GROUPS.put(id, groupInstance);
        } catch (Exception e) {
            LOGGER.error("Failed to load JSON item group {}", path.getFileName(), e);
        }
    }
}
