package zachy.plethora.common.crafting;

import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import zachy.plethora.common.block.ModBlocks;
import zachy.plethora.common.core.helper.CompressionHelper;

public class ModCraftingRecipes {

    public static void init() {
        /*for (int i = 0; i < CompressionHelper.VARIANT_NAME.size(); i++) {
            GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.compressedBlock[i],1 , 0), "BBB", "BBB", "BBB", 'B', new ItemStack(GameData.getBlockRegistry().getObject(CompressionHelper.VARIANT_MOD_ID.get(i) + ":" + CompressionHelper.VARIANT_NAME.get(i))));
            for (int j = 0; j < 7; j++) {
                GameRegistry.addShapedRecipe(new ItemStack(ModBlocks.compressedBlock[i], 1, j + 1), "CCC", "CCC", "CCC", 'C', new ItemStack(ModBlocks.compressedBlock[i], 1, j));
            }
        }*/
    }

}
