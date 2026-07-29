package sircow.torrential.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public class CacheItem extends Item {
    private final int size;

    public CacheItem(Properties properties, int size) {
        super(properties);
        this.size = size;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public @NonNull InteractionResult use(@NonNull Level level, @NonNull Player player, @NonNull InteractionHand usedHand) {
        return super.use(level, player, usedHand);
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
