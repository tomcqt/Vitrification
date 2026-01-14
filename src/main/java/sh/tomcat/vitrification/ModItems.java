package sh.tomcat.vitrification;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    private static Item amariteLongsword = null;

    public static Item getAmariteLongsword() {
        if (amariteLongsword == null) {
            amariteLongsword = Registries.ITEM.get(new Identifier("amarite", "amarite_longsword"));
        }
        return amariteLongsword;
    }

    public static final Item CREATIVE_TAB_ICON =
            Registry.register(Registries.ITEM,
                    new Identifier(Vitrification.MOD_ID, "creative_tab_icon"),
                    new Item(new FabricItemSettings()));

    public static void register() {
        Vitrification.LOGGER.info("Registering items for " + Vitrification.MOD_ID);
    }
}
