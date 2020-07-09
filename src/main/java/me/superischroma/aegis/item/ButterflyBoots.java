package me.superischroma.aegis.item;

import org.bukkit.Material;

public class ButterflyBoots extends AegisItem
{
    public ButterflyBoots()
    {
        super("Butterfly Boots", Material.IRON_BOOTS, Rarity.RARE);
        Recipe recipe = new Recipe("   ", "S S", "S S");
        recipe.setIngredient('S', new StrongFeather());
        super.setRecipe(recipe);
    }
}