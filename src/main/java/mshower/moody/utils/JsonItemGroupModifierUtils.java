package mshower.moody.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
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
    public List<JsonElement> items;

    public static final Map<String, ItemGroup> CUSTOM_GROUPS = new HashMap<>();

    public static void load(Path path) {
        Gson gson = new Gson();

        try (Reader reader = Files.newBufferedReader(path)) {
            JsonItemGroupModifierUtils json = gson.fromJson(reader, JsonItemGroupModifierUtils.class);
            String id = json.name.toLowerCase().replace(" ", "_");
            String iconIdStr;
            if (json.icon != null && !json.icon.isBlank()) {
                iconIdStr = json.icon;
            } else {
                iconIdStr = null;
                for (JsonElement element : json.items) {
                    iconIdStr = extractItemId(element);
                    if (iconIdStr != null) break;
                }
            }
            if (iconIdStr == null) {
                LOGGER.warn("Item group {} has no valid icon, using minecraft:stone", json.name);
                iconIdStr = "minecraft:stone";
            }
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
                                for (JsonElement element : json.items) {
                                    ItemStack stack = parseItemStack(element);
                                    if (!stack.isEmpty()) {
                                        entries.add(stack);
                                    }
                                }
                            })
                            .build()
            );
            CUSTOM_GROUPS.put(id, groupInstance);
        } catch (Exception e) {
            LOGGER.error("Failed to load JSON item group {}", path.getFileName(), e);
        }
    }

    private static ItemStack parseItemStack(JsonElement element) {
        try {
            if (element.isJsonPrimitive()) {
                Identifier id = new Identifier(element.getAsString());
                Item item = Registries.ITEM.get(id);
                return item == Items.AIR ? ItemStack.EMPTY : new ItemStack(item);
            }

            if (element.isJsonObject()) {
                var obj = element.getAsJsonObject();

                Identifier id = new Identifier(obj.get("item").getAsString());
                Item item = Registries.ITEM.get(id);
                if (item == Items.AIR) return ItemStack.EMPTY;

                ItemStack stack = new ItemStack(item);

                if (obj.has("nbt")) {
                    String nbtString = obj.get("nbt").toString();
                    NbtCompound nbt = StringNbtReader.parse(nbtString);
                    stack.setNbt(nbt);
                }

                return stack;
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to parse item entry: {}", element, e);
        }

        return ItemStack.EMPTY;
    }

    private static String extractItemId(JsonElement element) {
        if (element == null) return null;

        if (element.isJsonPrimitive()) {
            return element.getAsString();
        }

        if (element.isJsonObject() && element.getAsJsonObject().has("item")) {
            return element.getAsJsonObject().get("item").getAsString();
        }

        return null;
    }
}
