package me.superischroma.aegis.item;

import org.bukkit.Material;

public class ReinforcerStone extends AegisItem
{
    public ReinforcerStone()
    {
        super("Reinforcer Stone", Material.DIAMOND, Rarity.UNCOMMON);
        Recipe recipe = new Recipe("DDD", "DOD", "DDD");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('O', Material.OBSIDIAN);
        super.setRecipe(recipe);
        super.glow();
        super.applyMetaToStack();
    }
}
