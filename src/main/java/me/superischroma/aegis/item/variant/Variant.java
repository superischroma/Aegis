package me.superischroma.aegis.item.variant;

import lombok.Getter;
import me.superischroma.aegis.util.CompactAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.List;

public class Variant
{
    @Getter
    private String name;

    @Getter
    private List<CompactAttribute> attributes;

    public Variant(String name)
    {
        this.name = name;
        this.attributes = new ArrayList<>();
    }

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation, EquipmentSlot slot)
    {
        attributes.add(new CompactAttribute(attr, amount, operation, slot));
    }

    public void addAttribute(Attribute attr, double amount, EquipmentSlot slot)
    {
        attributes.add(new CompactAttribute(attr, amount, slot));
    }

    public void addAttribute(Attribute attr, double amount)
    {
        attributes.add(new CompactAttribute(attr, amount));
    }

    public static Variant getByName(String name)
    {
        for (VariantType type : VariantType.values())
        {
            Variant variant = type.newInstance();
            if (variant == null)
                continue;
            if (variant.getName().equals(name))
                return variant;
        }
        return null;
    }
}