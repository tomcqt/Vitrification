package sh.tomcat.vitrification.network;

import net.minecraft.util.Identifier;

import static sh.tomcat.vitrification.Vitrification.MOD_ID;

public final class VitrificationPackets {
    public static final Identifier ACTION_BAR =
            new Identifier(MOD_ID, "action_bar");

    private VitrificationPackets() {}
}