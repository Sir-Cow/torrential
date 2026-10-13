package sircow.torrential.config;

import com.google.gson.*;
import net.minecraft.resources.Identifier;
import sircow.torrential.Constants;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String CLIENT_FILE_NAME = "torrential-client.json";
    private static final String SERVER_FILE_NAME = "torrential-server.json";

    private static ClientModConfig clientConfig = new ClientModConfig();
    private static ServerModConfig serverConfig = new ServerModConfig();

    private ConfigManager() {}

    public static void loadClient(Path configDirectory) {
        Path configFile = configDirectory.resolve(CLIENT_FILE_NAME);

        try {
            Files.createDirectories(configDirectory);
        }
        catch (IOException exception) {
            Constants.LOG.error("Failed to create the config directory", exception);
            clientConfig = new ClientModConfig();
            return;
        }

        if (!Files.exists(configFile)) {
            clientConfig = new ClientModConfig();
            saveClient(configDirectory);
            return;
        }

        boolean missingKeys;

        try (Reader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            JsonElement element = JsonParser.parseReader(reader);

            if (!element.isJsonObject()) throw new IOException("Config root is not a JSON object");

            JsonObject json = element.getAsJsonObject();
            clientConfig = fromJson(json, new ClientModConfig());
            JsonObject defaults = GSON.toJsonTree(new ClientModConfig()).getAsJsonObject();
            missingKeys = !json.keySet().containsAll(defaults.keySet());
        }
        catch (Exception exception) {
            Constants.LOG.error("Failed to load {}, using defaults", configFile, exception);
            clientConfig = new ClientModConfig();
            missingKeys = true;
        }

        if (missingKeys) saveClient(configDirectory);
    }

    public static void loadServer(Path configDirectory) {
        Path configFile = configDirectory.resolve(SERVER_FILE_NAME);

        try {
            Files.createDirectories(configDirectory);
        }
        catch (IOException exception) {
            Constants.LOG.error("Failed to create the config directory", exception);
            serverConfig = new ServerModConfig();
            return;
        }

        if (!Files.exists(configFile)) {
            serverConfig = new ServerModConfig();
            saveServer(configDirectory);
            return;
        }

        boolean missingKeys;

        try (Reader reader = Files.newBufferedReader(configFile, StandardCharsets.UTF_8)) {
            JsonElement element = JsonParser.parseReader(reader);

            if (!element.isJsonObject()) throw new IOException("Config root is not a JSON object");

            JsonObject json = element.getAsJsonObject();
            serverConfig = fromJson(json, new ServerModConfig());
            JsonObject defaults = GSON.toJsonTree(new ServerModConfig()).getAsJsonObject();
            missingKeys = !json.keySet().containsAll(defaults.keySet());
        }
        catch (Exception exception) {
            Constants.LOG.error("Failed to load {}, using defaults", configFile, exception);
            serverConfig = new ServerModConfig();
            missingKeys = true;
        }

        if (missingKeys) saveServer(configDirectory);
    }

    public static void saveClient(Path configDirectory) {
        Path configFile = configDirectory.resolve(CLIENT_FILE_NAME);
        JsonObject json = new JsonObject();

        json.addProperty("enableRodTooltip", clientConfig.enableRodTooltip);

        write(configFile, json);
    }

    public static void saveServer(Path configDirectory) {
        Path configFile = configDirectory.resolve(SERVER_FILE_NAME);
        JsonObject json = new JsonObject();

        json.addProperty("enableConduitDamage", serverConfig.enableConduitDamage);
        json.addProperty("conduitDamageRadius", serverConfig.conduitDamageRadius);
        json.addProperty("conduitEffectMultiplier", serverConfig.conduitEffectMultiplier);
        JsonArray conduitDamageBlacklist = new JsonArray();
        for (String entry : serverConfig.conduitDamageBlacklist) conduitDamageBlacklist.add(entry);
        json.add("conduitDamageBlacklist", conduitDamageBlacklist);
        json.addProperty("riptideOutsideWater", serverConfig.riptideOutsideWater);

        json.addProperty("copperDurability", serverConfig.copperDurability);
        json.addProperty("ironDurability", serverConfig.ironDurability);
        json.addProperty("prismarineDurability", serverConfig.prismarineDurability);
        json.addProperty("diamondDurability", serverConfig.diamondDurability);
        json.addProperty("netheriteDurability", serverConfig.netheriteDurability);

        json.addProperty("hookSpeedCopper", serverConfig.hookSpeedCopper);
        json.addProperty("hookSpeedIron", serverConfig.hookSpeedIron);
        json.addProperty("hookSpeedPrismarine", serverConfig.hookSpeedPrismarine);
        json.addProperty("hookSpeedDiamond", serverConfig.hookSpeedDiamond);
        json.addProperty("hookSpeedNetherite", serverConfig.hookSpeedNetherite);

        json.addProperty("lineFortuneCopper", serverConfig.lineFortuneCopper);
        json.addProperty("lineFortuneIron", serverConfig.lineFortuneIron);
        json.addProperty("lineFortunePrismarine", serverConfig.lineFortunePrismarine);
        json.addProperty("lineFortuneDiamond", serverConfig.lineFortuneDiamond);
        json.addProperty("lineFortuneNetherite", serverConfig.lineFortuneNetherite);

        json.addProperty("sinkerLuckCopper", serverConfig.sinkerLuckCopper);
        json.addProperty("sinkerLuckIron", serverConfig.sinkerLuckIron);
        json.addProperty("sinkerLuckPrismarine", serverConfig.sinkerLuckPrismarine);
        json.addProperty("sinkerLuckDiamond", serverConfig.sinkerLuckDiamond);
        json.addProperty("sinkerLuckNetherite", serverConfig.sinkerLuckNetherite);

        json.addProperty("conduitLureBonus", serverConfig.conduitLureBonus);
        json.addProperty("breathLureBonus", serverConfig.breathLureBonus);
        json.addProperty("conduitLuckBonus", serverConfig.conduitLuckBonus);
        json.addProperty("breathLuckBonus", serverConfig.breathLuckBonus);

        write(configFile, json);
    }

    public static ClientModConfig getClient() {
        return clientConfig;
    }

    public static ServerModConfig getServer() {
        return serverConfig;
    }

    private static ClientModConfig fromJson(JsonObject json, ClientModConfig config) {
        config.enableRodTooltip = getBoolean(json, "enableRodTooltip", config.enableRodTooltip);

        return config;
    }

    private static ServerModConfig fromJson(JsonObject json, ServerModConfig config) {
        config.enableConduitDamage = getBoolean(json, "enableConduitDamage", config.enableConduitDamage);
        config.conduitDamageRadius = getInt(json, "conduitDamageRadius", config.conduitDamageRadius, 4, 32);
        config.conduitEffectMultiplier = getInt(json, "conduitEffectMultiplier", config.conduitEffectMultiplier, 4, 32);
        config.conduitDamageBlacklist = getEntityIdList(json, "conduitDamageBlacklist", config.conduitDamageBlacklist);
        config.riptideOutsideWater = getBoolean(json, "riptideOutsideWater", config.riptideOutsideWater);

        config.copperDurability = getInt(json, "copperDurability", config.copperDurability, 1, 100000);
        config.ironDurability = getInt(json, "ironDurability", config.ironDurability, 1, 100000);
        config.prismarineDurability = getInt(json, "prismarineDurability", config.prismarineDurability, 1, 100000);
        config.diamondDurability = getInt(json, "diamondDurability", config.diamondDurability, 1, 100000);
        config.netheriteDurability = getInt(json, "netheriteDurability", config.netheriteDurability, 1, 100000);

        config.hookSpeedCopper = getInt(json, "hookSpeedCopper", config.hookSpeedCopper, 0, 1000);
        config.hookSpeedIron = getInt(json, "hookSpeedIron", config.hookSpeedIron, 0, 1000);
        config.hookSpeedPrismarine = getInt(json, "hookSpeedPrismarine", config.hookSpeedPrismarine, 0, 1000);
        config.hookSpeedDiamond = getInt(json, "hookSpeedDiamond", config.hookSpeedDiamond, 0, 1000);
        config.hookSpeedNetherite = getInt(json, "hookSpeedNetherite", config.hookSpeedNetherite, 0, 1000);

        config.lineFortuneCopper = getDouble(json, "lineFortuneCopper", config.lineFortuneCopper, 0.0D, 100.0D);
        config.lineFortuneIron = getDouble(json, "lineFortuneIron", config.lineFortuneIron, 0.0D, 100.0D);
        config.lineFortunePrismarine = getDouble(json, "lineFortunePrismarine", config.lineFortunePrismarine, 0.0D, 100.0D);
        config.lineFortuneDiamond = getDouble(json, "lineFortuneDiamond", config.lineFortuneDiamond, 0.0D, 100.0D);
        config.lineFortuneNetherite = getDouble(json, "lineFortuneNetherite", config.lineFortuneNetherite, 0.0D, 100.0D);

        config.sinkerLuckCopper = getDouble(json, "sinkerLuckCopper", config.sinkerLuckCopper, 0.0D, 100.0D);
        config.sinkerLuckIron = getDouble(json, "sinkerLuckIron", config.sinkerLuckIron, 0.0D, 100.0D);
        config.sinkerLuckPrismarine = getDouble(json, "sinkerLuckPrismarine", config.sinkerLuckPrismarine, 0.0D, 100.0D);
        config.sinkerLuckDiamond = getDouble(json, "sinkerLuckDiamond", config.sinkerLuckDiamond, 0.0D, 100.0D);
        config.sinkerLuckNetherite = getDouble(json, "sinkerLuckNetherite", config.sinkerLuckNetherite, 0.0D, 100.0D);

        config.conduitLureBonus = getInt(json, "conduitLureBonus", config.conduitLureBonus, 0, 1000);
        config.breathLureBonus = getInt(json, "breathLureBonus", config.breathLureBonus, 0, 1000);
        config.conduitLuckBonus = getDouble(json, "conduitLuckBonus", config.conduitLuckBonus, 0.0D, 100.0D);
        config.breathLuckBonus = getDouble(json, "breathLuckBonus", config.breathLuckBonus, 0.0D, 100.0D);

        return config;
    }

    private static void write(Path configFile, JsonObject json) {
        Path temporaryFile = configFile.resolveSibling(configFile.getFileName() + ".tmp");

        try {
            Files.createDirectories(configFile.getParent());

            try (Writer writer = Files.newBufferedWriter(temporaryFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
                GSON.toJson(json, writer);
            }

            try {
                Files.move(temporaryFile, configFile, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            }
            catch (AtomicMoveNotSupportedException exception) {
                Files.move(temporaryFile, configFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException exception) {
            Constants.LOG.error("Failed to save {}", configFile, exception);
        }
    }

    private static boolean getBoolean(JsonObject json, String key, boolean defaultValue) {
        JsonElement element = json.get(key);

        if (element == null || !element.isJsonPrimitive()) return defaultValue;

        try {
            return element.getAsBoolean();
        }
        catch (RuntimeException exception) {
            return defaultValue;
        }
    }

    private static int getInt(JsonObject json, String key, int defaultValue, int minimum, int maximum) {
        JsonElement element = json.get(key);

        if (element == null || !element.isJsonPrimitive()) return defaultValue;

        try {
            return Math.clamp(element.getAsInt(), minimum, maximum);
        }
        catch (RuntimeException exception) {
            return defaultValue;
        }
    }

    private static double getDouble(JsonObject json, String key, double defaultValue, double minimum, double maximum) {
        JsonElement element = json.get(key);

        if (element == null || !element.isJsonPrimitive()) return defaultValue;

        try {
            double value = element.getAsDouble();

            if (!Double.isFinite(value)) {
                return defaultValue;
            }

            return Math.clamp(value, minimum, maximum);
        }
        catch (RuntimeException exception) {
            return defaultValue;
        }
    }

    private static List<String> getEntityIdList(JsonObject json, String key, List<String> defaultValue) {
        JsonElement element = json.get(key);

        if (element == null || !element.isJsonArray()) return defaultValue;

        List<String> result = new ArrayList<>();

        for (JsonElement entry : element.getAsJsonArray()) {
            if (!entry.isJsonPrimitive()) continue;

            String value = entry.getAsString().trim();
            if (value.isEmpty()) continue;

            if (Identifier.tryParse(value.startsWith("#") ? value.substring(1) : value) == null) {
                Constants.LOG.warn("Ignoring invalid entry \"{}\" in {}", value, key);
                continue;
            }

            result.add(value);
        }

        return result;
    }
}
