package mshower.moody.mixin;

import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Calendar;

import static mshower.moody.MoodyShower.config;

@Mixin(ChestBlockEntityRenderer.class)
public class ChristmasChestRendererMixin {
    @Shadow
    private boolean christmas;

    @Inject(
            method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V",
            at = @At("HEAD")
    )
    private void changeChristmasChestStatus(CallbackInfo ci) {
        if (config.forceControlChristmasChestRendering) {
            this.christmas = config.toggleChristmasChestRendering;
        }
        else {
            Calendar calendar = Calendar.getInstance();
            this.christmas = calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 24 && calendar.get(Calendar.DATE) <= 26;
        }
    }
}
