package sh.tomcat.vitrification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import sh.tomcat.vitrification.util.ItemModelData;
import sh.tomcat.vitrification.util.ItemModelRegistry;

import java.util.HashMap;
import java.util.Map;

public final class VitrificationModelSwapper {
    private static final Map<String, ModelIdentifier> MODEL_CACHE = new HashMap<>();

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

        ModelIdentifier modelIdentifier = MODEL_CACHE.computeIfAbsent(modelPath, path -> {
            Identifier id = new Identifier(path);
            return new ModelIdentifier(id, "inventory");
        });

        BakedModel customModel = MinecraftClient.getInstance()
                .getBakedModelManager()
                .getModel(modelIdentifier);

        if (customModel == null || customModel == MinecraftClient.getInstance()
                .getBakedModelManager().getMissingModel()) {
            return original;
        }

        return customModel;
    }
}
