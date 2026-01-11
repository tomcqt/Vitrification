package sh.tomcat.vitrification.util;

import eu.pb4.placeholders.api.TextParserUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

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
        updateLore(stack, data, next);
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

    private static void updateLore(ItemStack stack, ItemModelData data, int state) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = nbt.getCompound("display");

        if (state == 0 || !data.owners().containsKey(state)) {
            // Remove lore when reverting to default model
            display.remove("Lore");
            if (display.isEmpty()) {
                nbt.remove("display");
            } else {
                nbt.put("display", display);
            }
            return;
        }

        String owner = data.owners().get(state);
        Text lineBreak = Text.literal("");
        Text loreText = Text.literal("For " + owner)
                .styled(style -> style
                        .withColor(0xff3281)
                        .withItalic(false)
                        .withBold(true));

        NbtList loreList = new NbtList();
        loreList.add(NbtString.of(Text.Serializer.toJson(lineBreak)));
        loreList.add(NbtString.of(Text.Serializer.toJson(loreText)));

        display.put("Lore", loreList);
        nbt.put("display", display);
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
