package sh.tomcat.vitrification.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import sh.tomcat.vitrification.ModItems;

import java.util.List;
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
                            1, "vitrification:item/voidpaw_requiem",
                            2, "vitrification:item/needle"
                    )
            )
    );
}
