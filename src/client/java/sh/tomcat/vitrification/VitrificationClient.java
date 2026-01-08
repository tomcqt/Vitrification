package sh.tomcat.vitrification;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import sh.tomcat.vitrification.util.ItemModelRegistry;

public class VitrificationClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerItemPredicates();
	}

	private void registerItemPredicates() {
		for (Item item : ItemModelRegistry.ITEMS.keySet()) {
			ModelPredicateProviderRegistry.register(
					item,
					new Identifier("v_model_id"),
					(stack, world, entity, seed) -> {
						if (!stack.hasNbt()) return 0;
                        assert stack.getNbt() != null;
                        return stack.getNbt().getInt("VModelId");
					}
			);
		}
	}
}