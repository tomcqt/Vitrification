package sh.tomcat.vitrification;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.text.Text;
import sh.tomcat.vitrification.network.VitrificationPackets;

public class VitrificationClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelLoadingPlugin.register(new VitrificationModels());

		ClientPlayNetworking.registerGlobalReceiver(
				VitrificationPackets.ACTION_BAR,
				(client, handler, buf, responseSender) -> {
					Text text = buf.readText();
					client.execute(() ->
							ActionBarNotification.send(text)
					);
				}
		);
	}
}