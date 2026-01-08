package sh.tomcat.vitrification;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item AMARITE_LONGSWORD = Registries.ITEM.get(
            new Identifier("amarite", "amarite_longsword")
    );
}
