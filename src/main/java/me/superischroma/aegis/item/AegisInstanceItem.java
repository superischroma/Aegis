package me.superischroma.aegis.item;

import lombok.Getter;
import lombok.Setter;
import me.superischroma.aegis.item.variant.Variant;
import me.superischroma.aegis.util.*;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AegisInstanceItem
{
    // the base item
    @Getter
    private AegisItem item;

    // this instance's variant
    @Setter
    @Getter
    private Variant variant;

    // this instance's enchantments
    @Getter
    List<CompactEnchantment> enchantments;

    private AegisInstanceItem(AegisItem item, Variant variant, List<CompactEnchantment> enchantments)
    {
        this.item = item;
        this.variant = variant;
        this.enchantments = enchantments;
    }

    public void addEnchantment(Enchantment enchantment, int level)
    {
        enchantments.add(new CompactEnchantment(enchantment, level));
    }

    public boolean alreadyHasEnchantment(Enchantment enchantment)
    {
        for (CompactEnchantment e : enchantments)
        {
            if (e.getEnchantment().getKey().getKey().equals(enchantment.getKey().getKey()))
                return true;
        }
        return false;
    }

    public ItemStack create()
    {
        ModifyableItemStack stack = ModifyableItemStack.from(item.getStack());
        if (variant != null)
        {
            for (CompactAttribute attribute : variant.getAttributes())
            {
                Attribute attr = attribute.getAttribute();
                double amount = attribute.getAmount();
                AttributeModifier.Operation operation = attribute.getOperation();
                EquipmentSlot slot = attribute.getSlot();
                if (slot == null)
                {
                    switch (item.getType())
                    {
                        case MELEE:
                        case TOOLS:
                        case RANGED:
                        {
                            stack.addAttribute(attr, amount, operation, EquipmentSlot.HAND);
                            stack.addAttribute(attr, amount, operation, EquipmentSlot.OFF_HAND);
                            break;
                        }
                        case ARMOR:
                        {
                            stack.addAttribute(attr, amount, operation, EquipmentSlot.HEAD);
                            stack.addAttribute(attr, amount, operation, EquipmentSlot.CHEST);
                            stack.addAttribute(attr, amount, operation, EquipmentSlot.LEGS);
                            stack.addAttribute(attr, amount, operation, EquipmentSlot.FEET);
                            break;
                        }
                        case NONE:
                        {
                            stack.addAttribute(attr, amount, operation, null);
                            break;
                        }
                    }
                }
                else
                    stack.addAttribute(attr, amount, operation, slot);
            }
            stack.setName(item.getRarity().getColor() + variant.getName() + " " + stack.getName());
        }
        else
            stack.setName(item.getRarity().getColor() + stack.getName());
        stack.removeLoreLine(item.getRarity().getName());
        stack.removeLoreLine(AegisItem.getMark());
        if (enchantments.size() != 0)
            stack.removeLoreLine(item.getEnchantmentColor() + "No Enchantments");
        for (CompactEnchantment enchantment : enchantments)
        {
            stack.addLoreLine(item.getEnchantmentColor() + AUtil.getStringEnchant(enchantment.getEnchantment()) + " " + enchantment.getLevel());
        }
        stack.addLoreLine(item.getRarity().getName());
        stack.addLoreLine(AegisItem.getMark());
        stack.apply();
        return stack;
    }

    public static AegisInstanceItem from(ItemStack stack)
    {
        Variant variant;
        List<CompactEnchantment> enchantments = new ArrayList<>();
        ModifyableItemStack m = ModifyableItemStack.from(stack);
        AegisItemType type = null;
        for (AegisItemType t : AegisItemType.values())
        {
            if (AegisItem.isValid(stack, t))
            {
                type = t;
            }
        }
        if (type == null)
            return null;
        AegisItem item = type.newInstance();
        if (item.isEnchantable())
        {
            List<String> textEnchantments = new ArrayList<>();
            for (String line : m.getLore())
            {
                List<String> spl = new ArrayList<>(Arrays.asList(line.split(" ")));
                if (spl.size() < 2)
                    continue;
                spl.remove(spl.size() - 1);
                String newLine = StringUtils.join(spl, " ").substring(2);
                if (AUtil.getEnchantFromString(newLine) != null)
                    textEnchantments.add(line.substring(2));
            }
            for (String textEnchantment : textEnchantments)
            {
                List<String> parts = new ArrayList<>(Arrays.asList(textEnchantment.split(" ")));
                int level = Integer.parseInt(parts.get(parts.size() - 1));
                parts.remove(parts.size() - 1);
                String name = StringUtils.join(parts, " ");
                enchantments.add(new CompactEnchantment(AUtil.getEnchantFromString(name), level));
            }
        }
        variant = Variant.getByName(ChatColor.stripColor(m.getName().split(" ")[0]));
        return new AegisInstanceItem(item, variant, enchantments);
    }
}