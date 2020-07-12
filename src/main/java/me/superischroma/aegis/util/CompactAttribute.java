package me.superischroma.aegis.util;

import lombok.Getter;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;

public class CompactAttribute
{
    @Getter
    private final Attribute attribute;

    @Getter
    private final double amount;

    @Getter
    private final AttributeModifier.Operation operation;

    @Getter
    private final EquipmentSlot slot;

    public CompactAttribute(Attribute attribute, double amount, AttributeModifier.Operation operation, EquipmentSlot slot)
    {
        this.attribute = attribute;
        this.amount = amount;
        this.operation = operation;
        this.slot = slot;
    }

    public CompactAttribute(Attribute attribute, double amount, EquipmentSlot slot)
    {
        this(attribute, amount, AttributeModifier.Operation.ADD_NUMBER, slot);
    }

    public CompactAttribute(Attribute attribute, double amount)
    {
        this(attribute, amount, AttributeModifier.Operation.ADD_NUMBER, null);
    }
}