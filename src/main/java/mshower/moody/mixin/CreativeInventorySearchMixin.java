package mshower.moody.mixin;

import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Locale;
import java.util.Set;

import static mshower.moody.MoodyShower.config;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventorySearchMixin {

    @Shadow private TextFieldWidget searchBox;
    @Final @Shadow private Set<TagKey<Item>> searchResultTags;
    @Shadow private float scrollPosition;

    @Inject(method = "search", at = @At("HEAD"), cancellable = true)
    private void injectSearch(CallbackInfo ci) {
        String string = this.searchBox.getText();

        if (!config.toggleBetterCreativeInventorySearch) return;
        if (string.isEmpty()) return;
        char c = string.charAt(0);
        if (c != '@' && c != '$') return;

        ci.cancel();
        ScreenHandler handler = ((HandledScreenAccessorMixin) this).getHandler();
        CreativeInventoryScreen.CreativeScreenHandler h = (CreativeInventoryScreen.CreativeScreenHandler) handler;
        h.itemList.clear();
        this.searchResultTags.clear();
        String query = string.substring(1).toLowerCase(Locale.ROOT);

        if (c == '@') {
            for (Item item : Registries.ITEM) {
                if (item == Items.AIR) continue;
                Identifier id = Registries.ITEM.getId(item);
                if (id.toString().toLowerCase(Locale.ROOT).contains(query)) {
                    h.itemList.add(new ItemStack(item));
                }
            }
        }
        if (c == '$') {
            for (Item item : Registries.ITEM) {
                if (item == Items.AIR) continue;
                ItemStack stack = new ItemStack(item);
                Identifier id = Registries.ITEM.getId(item);
                String namespace = id.getNamespace();
                if (namespace.toLowerCase(Locale.ROOT).contains(query)) {
                    h.itemList.add(stack);
                }
            }
        }
        this.scrollPosition = 0.0F;
        h.scrollItems(0.0F);
    }
}
