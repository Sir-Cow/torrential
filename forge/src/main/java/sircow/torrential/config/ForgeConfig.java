package sircow.torrential.config;

import net.minecraftforge.fml.loading.FMLPaths;

public final class ForgeConfig {
    private ForgeConfig() {}

    public static void loadClient() {
        ConfigManager.loadClient(FMLPaths.CONFIGDIR.get());
    }

    public static void loadServer() {
        ConfigManager.loadServer(FMLPaths.CONFIGDIR.get());
    }

    public static void saveClient() {
        ConfigManager.saveClient(FMLPaths.CONFIGDIR.get());
    }

    public static void saveServer() {
        ConfigManager.saveServer(FMLPaths.CONFIGDIR.get());
    }
}
