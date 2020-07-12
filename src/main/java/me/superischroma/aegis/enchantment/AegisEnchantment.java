package me.superischroma.aegis.enchantment;

import org.bukkit.enchantments.Enchantment;

public enum AegisEnchantment
{
    FULL_FORCE(FullForce.class);

    private final Class<? extends Enchantment> clazz;

    AegisEnchantment(Class<? extends Enchantment> clazz)
    {
        this.clazz = clazz;
    }

    public Enchantment newInstance()
    {
        Enchantment enchantment;
        try
        {
            enchantment = clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex)
        {
            return null;
        }
        return enchantment;
    }

    public static AegisEnchantment findEnchantment(String s)
    {
        return valueOf(s.toUpperCase());
    }
}