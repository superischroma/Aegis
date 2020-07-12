package me.superischroma.aegis.util;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModifyableItemStack extends ItemStack
{
    private ItemMeta meta;
    @Getter
    private List<String> lore;

    private ModifyableItemStack(ItemStack stack)
    {
        super(stack == null ? Material.AIR : stack.getType(), stack == null ? 1 : stack.getAmount());
        this.setItemMeta(stack == null ? this.getItemMeta() : stack.getItemMeta());
        this.meta = this.getItemMeta();
        this.lore = this.getItemMeta().getLore();
    }

    public void addEnchant(Enchantment enchantment, int level)
    {
        meta.addEnchant(enchantment, level, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    }

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation, EquipmentSlot slot)
    {
        meta.addAttributeModifier(attr, new AttributeModifier(UUID.randomUUID(), AUtil.getStringAttribute(attr), amount, operation, slot));
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    }

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation)
    {
        addAttribute(attr, amount, operation, null);
    }

    public void addAttribute(Attribute attr, double amount, EquipmentSlot slot)
    {
        addAttribute(attr, amount, AttributeModifier.Operation.ADD_NUMBER, slot);
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
        meta.setLore(lore);
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

    public void addLoreLine(String line)
    {
        if (!meta.hasLore())
            meta.setLore(new ArrayList<>());
        lore.add(line);
    }

    public void setLoreLine(int index, String newLine)
    {
        if (!meta.hasLore())
            return;
        lore.set(index, newLine);
    }

    public String getLoreLine(int index)
    {
        return lore.get(index);
    }

    public void removeLoreLine(String line)
    {
        lore.remove(line);
    }

    public void removeLineLine(int index)
    {
        lore.remove(index);
    }

    public static ModifyableItemStack from(ItemStack stack)
    {
        return new ModifyableItemStack(stack);
    }
}