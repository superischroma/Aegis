package me.superischroma.aegis.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

public class FuseBow extends AegisItem
{
    public FuseBow()
    {
        super("Fuse Bow", Material.BOW, Rarity.LEGENDARY, ItemType.RANGED);
        Recipe recipe = new Recipe(" DC", "D C", " DC");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('C', new CondensedFuseSoul());
        super.setRecipe(recipe);
        super.setEnchantmentColor(ChatColor.YELLOW);
        super.addEnchant(Enchantment.ARROW_DAMAGE, 10);
        super.addEnchant(Enchantment.ARROW_KNOCKBACK, 5);
        super.applyMetaToStack();
    }
}
