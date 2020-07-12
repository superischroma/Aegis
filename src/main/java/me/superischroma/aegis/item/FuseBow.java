package me.superischroma.aegis.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FuseBow extends Weapon
{
    public FuseBow()
    {
        super("Fuse Bow", Material.BOW, Rarity.LEGENDARY, false, 250.0, 100);
        super.setEnchantmentColor(ChatColor.YELLOW);
        super.enchantable();
        Recipe recipe = new Recipe(" DC", "D C", " DC");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('C', new CondensedFuseSoul());
        super.setRecipe(recipe);
        super.addLoreLine(ChatColor.YELLOW + "Ability: Explosion");
        super.addLoreLine(ChatColor.GRAY + "Create an explosion on arrow");
        super.addLoreLine(ChatColor.GRAY + "impact based on bow force!");
        super.applyMetaToStack();
    }
}
