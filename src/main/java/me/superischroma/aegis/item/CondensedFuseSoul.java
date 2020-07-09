package me.superischroma.aegis.item;

import org.bukkit.Material;

public class CondensedFuseSoul extends AegisItem
{
    public CondensedFuseSoul()
    {
        super("Condensed Fuse Soul", Material.SOUL_SOIL, Rarity.RARE);
        Recipe recipe = new Recipe("SSS", "SSS", "SSS");
        recipe.setIngredient('S', new FuseSoul());
        super.setRecipe(recipe);
        super.glow();
        super.applyMetaToStack();
    }
}
