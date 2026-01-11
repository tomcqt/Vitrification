package sh.tomcat.vitrification;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import sh.tomcat.vitrification.util.ItemModelData;
import sh.tomcat.vitrification.util.ItemModelRegistry;

public class ModItemGroups {
    public static ItemGroup VITRIFICATION_GROUP;

    private static void applyCustomName(ItemStack stack, ItemModelData data, int modelId) {
        String nameFormat = data.names().get(modelId);
        if (nameFormat != null) {
            var name = eu.pb4.placeholders.api.TextParserUtils.formatText(nameFormat)
                    .copy().styled(style -> style.withItalic(false));
            stack.setCustomName(name);
        }
    }

    private static void applyCustomLore(ItemStack stack, ItemModelData data, int modelId) {
        NbtCompound nbt = stack.getOrCreateNbt();
        NbtCompound display = nbt.getCompound("display");

        String owner = data.owners().get(modelId);
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

    public static void register() {
        VITRIFICATION_GROUP = Registry.register(
                Registries.ITEM_GROUP,
                new Identifier(Vitrification.MOD_ID, "vitrification_tab"),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(ModItems.CREATIVE_TAB_ICON))
                        .displayName(Text.translatable("itemGroup.vitrification.vitrification_tab"))
                        .entries((context, entries) -> ItemModelRegistry.ITEMS.forEach((item, data) -> {
                            if (item == null) return;

                            for (int i = 1; i <= data.maxModel(); i++) {
                                ItemStack stack = new ItemStack(item);
                                if (stack.isEmpty()) continue;

                                stack.getOrCreateNbt().putInt("VModelId", i);

                                if (data.names().containsKey(i)) {
                                    applyCustomName(stack, data, i);
                                    applyCustomLore(stack, data, i);
                                }

                                entries.add(stack);
                            }
                        }))
                        .build()
        );

        Vitrification.LOGGER.info("Registering item groups for " + Vitrification.MOD_ID);
    }
}
