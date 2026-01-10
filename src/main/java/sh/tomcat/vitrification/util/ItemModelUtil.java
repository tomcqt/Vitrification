package sh.tomcat.vitrification.util;

import eu.pb4.placeholders.api.TextParserUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import sh.tomcat.vitrification.network.VitrificationPackets;

public class ItemModelUtil {
    public static void cycleModel(ItemStack stack, PlayerEntity player, World world) {
        ItemModelData data = ItemModelRegistry.ITEMS.get(stack.getItem());
        if (data == null) return; // item not supported

        NbtCompound nbt = stack.getOrCreateNbt();
        int current = nbt.getInt("VModelId");
        int next = (current + 1) > data.maxModel() ? 0 : current + 1;
        nbt.putInt("VModelId", next);

        player.setStackInHand(Hand.MAIN_HAND, stack);

        updateName(stack, data, next);
        playSound(player, world);
    }

    private static void updateName(ItemStack stack, ItemModelData data, int state) {
        if (state == 0) {
            stack.removeCustomName();
            return;
        }

        Text name = TextParserUtils.formatText(data.names().get(state)).copy().styled(style -> style.withItalic(false));
        if (name != null) {
            stack.setCustomName(name);
        } else {
            stack.removeCustomName();
        }
    }

    private static void playSound(PlayerEntity player, World world) {
        ItemStack stack = player.getMainHandStack();

        if (!stack.isEmpty() && ItemModelRegistry.ITEMS.containsKey(stack.getItem())) {
            world.playSound(
                    null,
                    player.getBlockPos(),
                    SoundEvents.BLOCK_SMITHING_TABLE_USE,
                    SoundCategory.PLAYERS,
                    1.0f,
                    1.0f
            );
        }
    }
}
