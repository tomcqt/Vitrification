package sh.tomcat.vitrification;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class ActionBarNotification {
    private ActionBarNotification() {}

    public static void send(Text text) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.inGameHud != null) {
            client.inGameHud.setOverlayMessage(text, false);
        }
    }
}