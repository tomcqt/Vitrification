package sh.tomcat.vitrification.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import sh.tomcat.vitrification.ModItems;

import java.util.Map;

public class ItemModelRegistry {
    public static final Map<Item, ItemModelData> ITEMS = Map.of(
            ModItems.AMARITE_LONGSWORD, new ItemModelData(
                    2,
                    Map.of(
                            1, "<b><gr:#665687:#B084CC>Voidpaw Requiem</gr></b>",
                            2, "<c:white><b>Needle</b></c>"
                    ),
                    Map.of(
                            0, "amarite:item/amarite_longsword",
                            1, "vitrification:item/voidpaw_requiem",
                            2, "vitrification:item/needle"
                    ),
                    Map.of(
                            1, "Alexandria",
                            2, "Hornet"
                    )
            ),
            Items.NETHERITE_SWORD, new ItemModelData(
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
            ),
            Items.NETHERITE_PICKAXE, new ItemModelData(
                    1,
                    Map.of(
                            1, "Entrencher"
                    ),
                    Map.of(
                            0, "minecraft:item/netherite_sword",
                            1, "vitrification:item/wwonepickaxe"
                    ),
                    Map.of(
                            1, "Excavator"
                    )
            ),
            Items.NETHERITE_SHOVEL, new ItemModelData(
                    1,
                    Map.of(
                            1, "Hatchet"
                    ),
                    Map.of(
                            0, "minecraft:item/netherite_sword",
                            1, "vitrification:item/wwonehatchet"
                    ),
                    Map.of(
                            1, "Excavator"
                    )
            ),
            Items.NETHERITE_AXE, new ItemModelData(
                    1,
                    Map.of(
                            1, "Spade"
                    ),
                    Map.of(
                            0, "minecraft:item/netherite_sword",
                            1, "vitrification:item/wwonespade"
                    ),
                    Map.of(
                            1, "Excavator"
                    )
            )
    );
}
