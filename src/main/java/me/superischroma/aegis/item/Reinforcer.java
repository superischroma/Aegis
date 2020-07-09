package me.superischroma.aegis.item;

import me.superischroma.aegis.gui.ReinforcerGUI;
import org.bukkit.Material;

public class Reinforcer extends AegisItem
{
    public Reinforcer()
    {
        super("Reinforcer", Material.BLAST_FURNACE, Rarity.UNCOMMON);
        super.setGUI(new ReinforcerGUI());
        Recipe recipe = new Recipe("CCC", "CSC", "CCC");
        recipe.setIngredient('C', Material.COBBLESTONE);
        recipe.setIngredient('S', new ReinforcerStone());
        super.setRecipe(recipe);
        super.glow();
        super.applyMetaToStack();
    }
}
