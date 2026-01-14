package sh.tomcat.vitrification.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import sh.tomcat.vitrification.ModItems;
import sh.tomcat.vitrification.Vitrification;

import java.util.HashMap;
import java.util.Map;

public class ItemModelRegistry {
    public static final Map<Item, ItemModelData> ITEMS = new HashMap<>();
    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;
        initialized = true;

        Vitrification.LOGGER.info("Initializing ItemModelRegistry...");

        registerItem("amarite", "amarite_longsword", new ItemModelData(
                3,
                Map.of(
                        1, "<b><gr:#665687:#b084cc>Voidpaw Requiem</gr></b>",
                        2, "<c:white><b>Needle</b></c>",
                        3, "<c:dark_red><b>Flesh Manifester</b></c>"
                ),
                Map.of(
                        0, "amarite:item/amarite_longsword",
                        1, "vitrification:item/voidpaw_requiem",
                        2, "vitrification:item/needle",
                        3, "vitrification:item/flesh_manifester"
                ),
                Map.of(
                        1, "Alexandria",
                        2, "Hornet",
                        3, "Void Cyplus"
                )
        ));

        registerItem("minecraft", "netherite_sword", new ItemModelData(
                1,
                Map.of(
                        1, "Bayonet"
                ),
                Map.of(
                        0, "minecraft:item/netherite_sword",
                        1, "vitrification:item/wwonebayonet"
                ),
                Map.of(
                        1, "Excavator"
                )
        ));

        registerItem("minecraft", "netherite_pickaxe", new ItemModelData(
                1,
                Map.of(
                        1, "Entrencher"
                ),
                Map.of(
                        0, "minecraft:item/netherite_pickaxe",
                        1, "vitrification:item/wwonepickaxe"
                ),
                Map.of(
                        1, "Excavator"
                )
        ));

        registerItem("minecraft", "netherite_axe", new ItemModelData(
                1,
                Map.of(
                        1, "Hatchet"
                ),
                Map.of(
                        0, "minecraft:item/netherite_axe",
                        1, "vitrification:item/wwonehatchet"
                ),
                Map.of(
                        1, "Excavator"
                )
        ));

        registerItem("minecraft", "netherite_shovel", new ItemModelData(
                1,
                Map.of(
                        1, "Spade"
                ),
                Map.of(
                        0, "minecraft:item/netherite_shovel",
                        1, "vitrification:item/wwonespade"
                ),
                Map.of(
                        1, "Excavator"
                )
        ));

        Vitrification.LOGGER.info("ItemModelRegistry initialized with {} items", ITEMS.size());
    }

    private static void registerItem(String modId, String itemName, ItemModelData data) {
        Identifier itemId = new Identifier(modId, itemName);
        Item item = Registries.ITEM.get(itemId);

        // Check if we got the actual item (not air)
        if (item == Items.AIR) {
            Vitrification.LOGGER.warn("Item not found in registry: {} - skipping", itemId);
            return;
        }

        ITEMS.put(item, data);
        Vitrification.LOGGER.info("Registered model data for: {}", itemId);
    }
}
