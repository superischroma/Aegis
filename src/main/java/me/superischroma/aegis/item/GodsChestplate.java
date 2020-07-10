package me.superischroma.aegis.item;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class GodsChestplate extends Armor
{
    public GodsChestplate()
    {
        super("God's Chestplate", Material.LEATHER_CHESTPLATE, Rarity.LEGENDARY, 200, 100);
        super.enchantable();
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(255, 255, 186));
        Recipe recipe = new Recipe("E E", "EEE", "EEE");
        recipe.setIngredient('E', new GodsEssence());
        super.setRecipe(recipe);
        super.addLoreLine("");
        super.addLoreLine(ChatColor.GOLD + "Full Set Bonus: God's Strength");
        super.addLoreLine(ChatColor.RED + "+100 Health");
        super.addLoreLine(ChatColor.GREEN + "+50 Defense");
        super.applyMetaToStack();
    }
}