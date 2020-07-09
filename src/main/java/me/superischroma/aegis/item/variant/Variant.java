package me.superischroma.aegis.item.variant;

import lombok.Getter;
import me.superischroma.aegis.util.CompactAttribute;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

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

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation)
    {
        attributes.add(new CompactAttribute(attr, amount, operation));
    }

    public void addAttribute(Attribute attr, double amount)
    {
        attributes.add(new CompactAttribute(attr, amount));
    }
}