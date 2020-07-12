package me.superischroma.aegis.item;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.EquipmentSlot;

public class Weapon extends AegisItem
{
    @Getter
    private final double damage;

    @Getter
    @Setter
    private final double speed;

    @Getter
    @Setter
    private final int strength;

    public Weapon(String name, Material type, Rarity rarity, boolean melee, double damage, int strength, double speed)
    {
        super(name, type, rarity, melee ? ItemType.MELEE : ItemType.RANGED);
        super.enchantable();
        this.damage = damage;
        this.strength = strength;
        this.speed = speed;
        super.addAttribute(Attribute.GENERIC_MOVEMENT_SPEED, speed, EquipmentSlot.HAND);
        super.addAttribute(Attribute.GENERIC_MOVEMENT_SPEED, speed, EquipmentSlot.OFF_HAND);
        super.addLoreLine(ChatColor.RED + "+" + ((int) damage) + " Damage");
        super.addLoreLine(ChatColor.RED + "+" + strength + " Strength");
        if (speed != 0.0)
            super.addLoreLine(ChatColor.AQUA + "+" + ((int) (speed * 1000)) + "% Speed");
        super.applyMetaToStack();
    }

    public Weapon(String name, Material type, Rarity rarity, boolean melee, double damage, int strength)
    {
        this(name, type, rarity, melee, damage, strength, 0.0);
    }
}