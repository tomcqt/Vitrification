package sh.tomcat.vitrification;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import sh.tomcat.vitrification.util.ItemModelData;
import sh.tomcat.vitrification.util.ItemModelRegistry;
import sh.tomcat.vitrification.util.ItemModelUtil;

public class SmithingTableHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!player.isSneaking()) return ActionResult.PASS;
            if (hand != Hand.MAIN_HAND) return ActionResult.PASS;
            if (!world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.SMITHING_TABLE)) return ActionResult.PASS;

            ItemStack stack = player.getStackInHand(hand);

            if (stack.isEmpty() || !ItemModelRegistry.ITEMS.containsKey(stack.getItem())) return ActionResult.PASS;

            ItemModelUtil.cycleModel(stack, player, world);
            return ActionResult.SUCCESS;
        });
    }
}
