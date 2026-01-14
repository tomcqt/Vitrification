package sh.tomcat.vitrification.mixin;

import dev.doctor4t.arsenal.cca.ArsenalComponents;
import dev.doctor4t.arsenal.cca.WeaponOwnerComponent;
import dev.doctor4t.arsenal.index.ArsenalCosmetics;
import dev.doctor4t.arsenal.item.ScytheItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.tomcat.vitrification.util.ItemModelData;
import sh.tomcat.vitrification.util.ItemModelRegistry;

@Mixin(ScytheItem.class)
public class ScytheItemMixin {

    @Unique
    private static final String VITRIFICATION_MODE_KEY = "VitrificationMode";

    @Inject(
            method = "useOnBlock",
            at = @At("HEAD"),
            cancellable = true
    )
    private void vitrification$onUseOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        BlockState blockStateClicked = context.getWorld().getBlockState(context.getBlockPos());
        PlayerEntity user = context.getPlayer();

        if (user == null || !user.isSneaking()) return;
        if (!blockStateClicked.isIn(BlockTags.ANVIL) && !blockStateClicked.isOf(Blocks.SMITHING_TABLE)) return;
        if (!context.getWorld().isClient) return;

        ItemStack stack = context.getStack();
        boolean isSupporter = ArsenalCosmetics.isSupporter(user.getUuid());

        if (isSupporter) {
            cir.setReturnValue(vitrification$handleSupporterSkinCycle(context, stack, user));
        } else {
            cir.setReturnValue(vitrification$handleVitrificationOnlyCycle(context, stack, user));
        }
    }

    @Unique
    private ActionResult vitrification$handleSupporterSkinCycle(ItemUsageContext context, ItemStack stack, PlayerEntity user) {
        NbtCompound nbt = stack.getOrCreateNbt();
        boolean inVitrificationMode = nbt.getBoolean(VITRIFICATION_MODE_KEY);

        if (!inVitrificationMode) {
            // Currently in Arsenal skin mode
            WeaponOwnerComponent weaponOwnerComponent = ArsenalComponents.WEAPON_OWNER_COMPONENT.get(stack);
            ScytheItem.Skin currentSkin = ScytheItem.Skin.fromString(ArsenalCosmetics.getSkin(stack));

            if (currentSkin == null) {
                currentSkin = ScytheItem.Skin.DEFAULT;
            }

            ScytheItem.Skin nextSkin = ScytheItem.Skin.getNext(currentSkin);

            if (nextSkin == ScytheItem.Skin.DEFAULT) {
                // Completed Arsenal skins, switch to Vitrification mode
                int firstVitId = vitrification$getFirstVitrificationModelId(stack);
                if (firstVitId > 0) {
                    nbt.putBoolean(VITRIFICATION_MODE_KEY, true);
                    nbt.putInt("VModelId", firstVitId);
                    ArsenalCosmetics.setSkin(weaponOwnerComponent.getOwner(), stack, ScytheItem.Skin.DEFAULT.getName());
                }
                // If no Vitrification skins, just go back to DEFAULT
            } else {
                ArsenalCosmetics.setSkin(weaponOwnerComponent.getOwner(), stack, nextSkin.getName());
            }
        } else {
            // Currently in Vitrification skin mode
            int currentModelId = nbt.getInt("VModelId");
            int nextModelId = vitrification$getNextVitrificationModelId(stack, currentModelId);

            if (nextModelId == 0) {
                // Exit Vitrification mode, go back to Arsenal DEFAULT
                nbt.putBoolean(VITRIFICATION_MODE_KEY, false);
                nbt.putInt("VModelId", 0);
            } else {
                nbt.putInt("VModelId", nextModelId);
            }
        }

        user.playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, 0.5f, 1.0f);
        return ActionResult.SUCCESS;
    }

    @Unique
    private ActionResult vitrification$handleVitrificationOnlyCycle(ItemUsageContext context, ItemStack stack, PlayerEntity user) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int currentModelId = nbt.getInt("VModelId");
        int nextModelId = vitrification$getNextVitrificationModelIdWithLoop(stack, currentModelId);

        nbt.putInt("VModelId", nextModelId);

        user.playSound(SoundEvents.BLOCK_SMITHING_TABLE_USE, 0.5f, 1.0f);
        return ActionResult.SUCCESS;
    }

    @Unique
    private int vitrification$getFirstVitrificationModelId(ItemStack stack) {
        ItemModelData data = ItemModelRegistry.ITEMS.get(stack.getItem());
        if (data == null || data.modelData().isEmpty()) {
            return 0;
        }

        return data.modelData().keySet().stream()
                .mapToInt(Integer::intValue)
                .filter(id -> id > 0)
                .min()
                .orElse(0);
    }

    @Unique
    private int vitrification$getNextVitrificationModelId(ItemStack stack, int currentId) {
        ItemModelData data = ItemModelRegistry.ITEMS.get(stack.getItem());
        if (data == null || data.modelData().isEmpty()) {
            return 0;
        }

        int[] validIds = data.modelData().keySet().stream()
                .mapToInt(Integer::intValue)
                .filter(id -> id > 0)
                .sorted()
                .toArray();

        if (validIds.length == 0) return 0;

        for (int id : validIds) {
            if (id > currentId) {
                return id;
            }
        }

        // Loop back to 0 (exit Vitrification mode)
        return 0;
    }

    @Unique
    private int vitrification$getNextVitrificationModelIdWithLoop(ItemStack stack, int currentId) {
        ItemModelData data = ItemModelRegistry.ITEMS.get(stack.getItem());
        if (data == null || data.modelData().isEmpty()) {
            return 0;
        }

        int[] validIds = data.modelData().keySet().stream()
                .mapToInt(Integer::intValue)
                .filter(id -> id > 0)
                .sorted()
                .toArray();

        if (validIds.length == 0) return 0;

        for (int id : validIds) {
            if (id > currentId) {
                return id;
            }
        }

        // Loop back to first Vitrification skin (for non-supporters who cycle through)
        return validIds[0];
    }
}