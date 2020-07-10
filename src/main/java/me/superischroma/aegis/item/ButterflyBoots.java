package me.superischroma.aegis.item;

import org.bukkit.Material;

public class ButterflyBoots extends Armor
{
    public ButterflyBoots()
    {
        super("Butterfly Boots", Material.IRON_BOOTS, Rarity.RARE, 50, 10);
        super.enchantable();
        Recipe recipe = new Recipe("   ", "S S", "S S");
        recipe.setIngredient('S', new StrongFeather());
        super.setRecipe(recipe);
    }
}