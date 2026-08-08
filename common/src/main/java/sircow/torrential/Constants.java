package sircow.torrential;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sircow.torrential.menu.AnglingTableMenu;
import sircow.torrential.menu.CacheMenu;

import java.util.function.Supplier;

public class Constants {
	public static final String MOD_ID = "torrential";
	public static final String MOD_NAME = "Torrential";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static ResourceLocation id(String name) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
	}

	public static Supplier<MenuType<AnglingTableMenu>> ANGLING_TABLE_MENU_TYPE;
	public static Supplier<MenuType<CacheMenu>> CACHE_MENU_TYPE;
}
