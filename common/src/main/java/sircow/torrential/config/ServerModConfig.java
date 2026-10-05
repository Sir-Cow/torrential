package sircow.torrential.config;

import java.util.List;

public final class ServerModConfig {
    public boolean enableConduitDamage = true;
    public int conduitDamageRadius = 16;
    public int conduitEffectMultiplier = 16;
    public List<String> conduitDamageBlacklist = List.of();
    public boolean riptideOutsideWater = true;

    public int copperDurability = 190;
    public int ironDurability = 250;
    public int prismarineDurability = 768;
    public int diamondDurability = 1562;
    public int netheriteDurability = 2032;

    public int hookSpeedCopper = 50;
    public int hookSpeedIron = 100;
    public int hookSpeedPrismarine = 150;
    public int hookSpeedDiamond = 200;
    public int hookSpeedNetherite = 300;

    public double lineFortuneCopper = 0.5D;
    public double lineFortuneIron = 1.0D;
    public double lineFortunePrismarine = 1.5D;
    public double lineFortuneDiamond = 2.0D;
    public double lineFortuneNetherite = 3.0D;

    public double sinkerLuckCopper = 0.5D;
    public double sinkerLuckIron = 1.0D;
    public double sinkerLuckPrismarine = 1.5D;
    public double sinkerLuckDiamond = 2.0D;
    public double sinkerLuckNetherite = 3.0D;

    public int conduitLureBonus = 50;
    public int breathLureBonus = 50;
    public double conduitLuckBonus = 0.5D;
    public double breathLuckBonus = 0.5D;

    public ServerModConfig() {}
}
