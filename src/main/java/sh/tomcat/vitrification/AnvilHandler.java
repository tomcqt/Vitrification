package sh.tomcat.vitrification;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import sh.tomcat.vitrification.network.VitrificationPackets;
import sh.tomcat.vitrification.util.ItemModelRegistry;

public class AnvilHandler {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (hand != Hand.MAIN_HAND) return ActionResult.PASS;

            BlockState hitBlock = world.getBlockState(hitResult.getBlockPos());

            if (!hitBlock.isOf(Blocks.ANVIL) && !hitBlock.isOf(Blocks.CHIPPED_ANVIL) && !hitBlock.isOf(Blocks.DAMAGED_ANVIL)) return ActionResult.PASS;

            ItemStack stack = player.getStackInHand(hand);

            if (stack.isEmpty() || !ItemModelRegistry.ITEMS.containsKey(stack.getItem())) return ActionResult.PASS;
            if (stack.getOrCreateNbt().getInt("VModelId") == 0) return ActionResult.PASS;

            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerPlayNetworking.send(
                        serverPlayer,
                        VitrificationPackets.ACTION_BAR,
                        PacketByteBufs.create().writeText(Text.literal("This item is not usable in an Anvil."))
                );
            }

            return ActionResult.SUCCESS;
        });
    }
}
