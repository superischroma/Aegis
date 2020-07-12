package me.superischroma.aegis.enchantment;

import me.superischroma.aegis.Aegis;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class FullForce extends Enchantment
{
    private static Aegis plugin = Aegis.getPlugin();

    public FullForce()
    {
        super(new NamespacedKey(plugin, "full_force"));
    }

    @Override
    public String getName()
    {
        return "Full Force";
    }

    @Override
    public int getMaxLevel()
    {
        return 1;
    }

    @Override
    public int getStartLevel()
    {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget()
    {
        return EnchantmentTarget.BOW;
    }

    @Override
    public boolean isTreasure()
    {
        return false;
    }

    @Override
    public boolean isCursed()
    {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment other)
    {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack item)
    {
        return true;
    }
}