package me.superischroma.aegis.util;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class ModifyableItemStack extends ItemStack
{
    private ItemMeta meta;

    private ModifyableItemStack(ItemStack stack)
    {
        super(stack == null ? Material.AIR : stack.getType(), stack == null ? 1 : stack.getAmount());
        this.setItemMeta(stack == null ? this.getItemMeta() : stack.getItemMeta());
        this.meta = this.getItemMeta();
    }

    public void addEnchant(Enchantment enchantment, int level)
    {
        meta.addEnchant(enchantment, level, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation)
    {
        meta.addAttributeModifier(attr, new AttributeModifier(AUtil.getStringAttribute(attr), amount, operation));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public void addAttribute(Attribute attr, double amount)
    {
        addAttribute(attr, amount, AttributeModifier.Operation.ADD_NUMBER);
    }

    public void setDurability(int durability)
    {
        ((Damageable) this.meta).setDamage(durability);
    }

    public void apply()
    {
        this.setItemMeta(meta);
    }

    public boolean hasName()
    {
        return meta.hasDisplayName();
    }

    public String getName()
    {
        if (!hasName())
            return "";
        return meta.getDisplayName();
    }

    public void setName(String name)
    {
        meta.setDisplayName(name);
    }

    public static ModifyableItemStack from(ItemStack stack)
    {
        return new ModifyableItemStack(stack);
    }
}