package sh.tomcat.vitrification;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.util.Identifier;
import sh.tomcat.vitrification.util.ItemModelRegistry;

public class VitrificationModels implements ModelLoadingPlugin {
    @Override
    public void onInitializeModelLoader(Context ctx) {
        // register custom models from the registry
        ItemModelRegistry.ITEMS.values().forEach(data -> data.modelData().values().forEach(path -> {
            Identifier id = new Identifier(path);
            ctx.addModels(id);

            // also register blocking variant if it exists
            if (path.equals("vitrification:item/voidpaw_requiem") || path.equals("vitrification:item/needle")) {
                Identifier blockingId = new Identifier(path + "_blocking");
                ctx.addModels(blockingId);
            }
        }));
    }
}
