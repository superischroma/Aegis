package me.superischroma.aegis.item;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class VeryDefensiveChestplate extends Armor
{
    public VeryDefensiveChestplate()
    {
        super("Very Defensive Chestplate", Material.LEATHER_CHESTPLATE, Rarity.SPECIAL, 0, 100000000);
        ((LeatherArmorMeta) meta).setColor(Color.BLACK);
        super.applyMetaToStack();
    }
}