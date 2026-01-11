package sh.tomcat.vitrification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import sh.tomcat.vitrification.util.ItemModelData;
import sh.tomcat.vitrification.util.ItemModelRegistry;

import java.util.HashMap;
import java.util.Map;

public final class VitrificationModelSwapper {
    private static final Map<String, Identifier> MODEL_CACHE = new HashMap<>();

    private VitrificationModelSwapper() {}

    public static BakedModel getModel(ItemStack stack, BakedModel original) {
        if (stack.isEmpty()) return original;

        ItemModelData data = ItemModelRegistry.ITEMS.get(stack.getItem());
        if (data == null) return original;

        int modelId;
        if (stack.hasNbt()) {
            assert stack.getNbt() != null;
            modelId = stack.getNbt().getInt("VModelId");
        } else {
            modelId = 0;
        }
        if (modelId == 0) return original;

        String modelPath = data.modelData().get(modelId);
        if (modelPath == null) return original;

        // Check if player is blocking and we have a blocking variant
        if (isPlayerBlocking(stack)) {
            String blockingPath = modelPath + "_blocking";
            Identifier blockingId = MODEL_CACHE.computeIfAbsent(blockingPath, Identifier::new);
            BakedModelManager manager = MinecraftClient.getInstance().getBakedModelManager();
            BakedModel blockingModel = manager.getModel(blockingId);
            
            if (blockingModel != null && blockingModel != manager.getMissingModel()) {
                return blockingModel;
            }
        }

        Identifier modelIdentifier = MODEL_CACHE.computeIfAbsent(modelPath, Identifier::new);

        BakedModelManager manager = MinecraftClient.getInstance().getBakedModelManager();
        BakedModel customModel = manager.getModel(modelIdentifier);

        if (customModel == null || customModel == manager.getMissingModel()) {
            return original;
        }

        return customModel;
    }

    private static boolean isPlayerBlocking(ItemStack stack) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return false;
        
        PlayerEntity player = client.player;
        return player.isUsingItem() && player.getActiveItem() == stack;
    }
}
