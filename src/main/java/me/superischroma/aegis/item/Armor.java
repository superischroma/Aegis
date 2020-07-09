package me.superischroma.aegis.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class Armor extends AegisItem
{
    @Getter
    @Setter
    private double health;

    @Getter
    @Setter
    private int defense;

    public Armor(String name, Material type, Rarity rarity, double health, int defense)
    {
        super(name, type, rarity, ItemType.ARMOR);
        super.addLoreLine(ChatColor.RED + "+" + ((int) health) + " Health");
        super.addLoreLine(ChatColor.GREEN + "+" + defense + " Defense");
        super.applyMetaToStack();
        this.health = health;
        this.defense = defense;
    }
}