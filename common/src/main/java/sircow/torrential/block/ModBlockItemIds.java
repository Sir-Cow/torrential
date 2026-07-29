package sircow.torrential.block;

import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;
import sircow.torrential.Constants;

public class ModBlockItemIds {
    public static final BlockItemId ANGLING_TABLE = create("angling_table");

    private static BlockItemId create(String name) {
        Identifier id = Constants.id(name);
        return BlockItemId.create(id, id);
    }
}
