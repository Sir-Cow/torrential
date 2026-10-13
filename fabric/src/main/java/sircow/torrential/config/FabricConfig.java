package sircow.torrential.config;

import net.fabricmc.loader.api.FabricLoader;

public final class FabricConfig {
    private FabricConfig() {}

    public static void loadClient() {
        ConfigManager.loadClient(FabricLoader.getInstance().getConfigDir());
    }

    public static void loadServer() {
        ConfigManager.loadServer(FabricLoader.getInstance().getConfigDir());
    }

    public static void saveClient() {
        ConfigManager.saveClient(FabricLoader.getInstance().getConfigDir());
    }

    public static void saveServer() {
        ConfigManager.saveServer(FabricLoader.getInstance().getConfigDir());
    }
}
