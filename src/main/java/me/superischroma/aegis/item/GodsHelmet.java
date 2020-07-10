package me.superischroma.aegis.item;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GodsHelmet extends Armor
{
    public GodsHelmet()
    {
        super("God's Helmet", Material.LEATHER_HELMET, Rarity.LEGENDARY, 100, 50);
        super.enchantable();
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(255, 255, 186));
        Recipe recipe = new Recipe("EEE", "E E", "   ");
        recipe.setIngredient('E', new GodsEssence());
        super.setRecipe(recipe);
        super.addLoreLine("");
        super.addLoreLine(ChatColor.GOLD + "Full Set Bonus: God's Strength");
        super.addLoreLine(ChatColor.RED + "+100 Health");
        super.addLoreLine(ChatColor.GREEN + "+50 Defense");
        super.applyMetaToStack();
    }
}