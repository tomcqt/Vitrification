package sh.tomcat.vitrification.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import sh.tomcat.vitrification.VitrificationModelSwapper;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {
    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private BakedModel vitrification_swapModel(BakedModel original,
                                               ItemStack stack,
                                               ModelTransformationMode mode,
                                               boolean leftHanded,
                                               MatrixStack matrices,
                                               VertexConsumerProvider consumers,
                                               int light,
                                               int overlay) {
        return VitrificationModelSwapper.getModel(stack, original);
    }
}
