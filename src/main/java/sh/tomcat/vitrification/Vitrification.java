package sh.tomcat.vitrification;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sh.tomcat.vitrification.util.ItemModelRegistry;
import sh.tomcat.vitrification.util.ItemModelUtil;

public class Vitrification implements ModInitializer {
	public static final String MOD_ID = "vitrification";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		SmithingTableHandler.register();
		LOGGER.info("It's Vitrificating time.");
	}
}