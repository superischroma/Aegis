package me.superischroma.aegis.item;

import me.superischroma.aegis.gui.HubGUI;
import org.bukkit.Material;

public class HubBlock extends AegisItem
{
    public HubBlock()
    {
        super("Hub Block", Material.TARGET, Rarity.COMMON);
        super.setGUI(new HubGUI());
        Recipe recipe = new Recipe("PPP", "PRP", "PPP");
        recipe.setIngredient('P', Material.PAPER);
        recipe.setIngredient('R', Material.REDSTONE);
        super.setRecipe(recipe);
        super.glow();
        super.applyMetaToStack();
    }
}
