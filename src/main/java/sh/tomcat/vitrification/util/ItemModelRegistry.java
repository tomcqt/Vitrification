package sh.tomcat.vitrification.util;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import sh.tomcat.vitrification.ModItems;
import eu.pb4.placeholders.api.TextParserUtils;

import java.util.Map;

public class ItemModelRegistry {
    public static final Map<Item, ItemModelData> ITEMS = Map.of(
            ModItems.AMARITE_LONGSWORD, new ItemModelData(
                    1,
                    Map.of(
                            1, TextParserUtils.formatText("<clear:italic><b><gr:#665687:#B084CC>Voidpaw Requiem</gr></b></clear>")
                    ),
                    Map.of(
                            1, "vitrification:item/voidpaw_requiem"
                    )
            )
    );
}
