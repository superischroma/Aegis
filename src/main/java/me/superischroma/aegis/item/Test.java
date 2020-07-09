package me.superischroma.aegis.item;

import org.bukkit.Material;

public class Test extends AegisItem
{
    public Test()
    {
        super("Test", Material.QUARTZ, Rarity.SPECIAL);
        Recipe recipe = new Recipe("   ", "BBB", "   ");
        recipe.setIngredient('B', Material.BEDROCK);
        super.setRecipe(recipe);
        super.glow();
        super.applyMetaToStack();
    }
}