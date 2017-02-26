package zachy.plethora.common.core.helper;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CompressionHelper {

    public static List<String> VARIANT_MOD_ID = new ArrayList<String>();
    public static List<String> VARIANT_NAME = new ArrayList<String>();
    public static List<String> VARIANT_TEXTURE_NAME = new ArrayList<String>();

    public static void getVariant(String[] compressedVariants) {
        String modID;
        String blockName;

        Block block;

        for (String variant : compressedVariants) {
            if (variant.isEmpty()) {
                continue;
            }

            block = GameData.getBlockRegistry().getObject(variant);

            if (block != null) {
                modID = variant.split(":")[0];
                blockName = variant.split(":")[1];

                VARIANT_MOD_ID.add(modID);
                VARIANT_NAME.add(Character.toUpperCase(blockName.charAt(0)) + blockName.substring(1));
                VARIANT_TEXTURE_NAME.add(blockName);
            }
        }
    }
}
