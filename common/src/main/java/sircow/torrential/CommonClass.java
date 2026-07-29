package sircow.torrential;

import sircow.torrential.block.ModBlocks;
import sircow.torrential.component.ModComponents;
import sircow.torrential.damage.ModDamageTypes;
import sircow.torrential.item.ModItems;
import sircow.torrential.platform.Services;
import sircow.torrential.potion.ModPotions;
import sircow.torrential.sound.ModSounds;
import sircow.torrential.tag.ModTags;

public class CommonClass {
    public static void init() {
        if (Services.PLATFORM.isModLoaded("torrential")) {
            Constants.LOG.info("Initialising " + Constants.MOD_NAME);
            // registering
            ModBlocks.registerModBlocks();
            ModItems.registerModItems();
            ModTags.registerModTags();
            ModComponents.registerModComponents();
            ModSounds.registerSounds();
            ModPotions.registerModPotions();
            ModDamageTypes.registerModDamageTypes();
        }
    }
}
