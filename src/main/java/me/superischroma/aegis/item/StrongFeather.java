package me.superischroma.aegis.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class StrongFeather extends AegisItem
{
    public StrongFeather()
    {
        super("Strong Feather", Material.FEATHER, Rarity.UNCOMMON);
        Recipe recipe = new Recipe(" F ", "FFF", " F ");
        recipe.setIngredient('F', new ItemStack(Material.FEATHER, 64));
        super.setRecipe(recipe);
        super.glow();
        super.applyMetaToStack();
    }
}