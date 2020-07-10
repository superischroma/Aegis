package me.superischroma.aegis.item.variant;

import org.bukkit.attribute.Attribute;

public class Test extends Variant
{
    public Test()
    {
        super("Tested");
        super.addAttribute(Attribute.GENERIC_MOVEMENT_SPEED, 0.5);
    }
}