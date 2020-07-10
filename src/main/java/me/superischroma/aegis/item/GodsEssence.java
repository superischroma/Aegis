package me.superischroma.aegis.item;

import org.bukkit.Material;

public class GodsEssence extends AegisItem
{
    public GodsEssence()
    {
        super("God's Essence", Material.GLOWSTONE_DUST, Rarity.EPIC);
        super.glow();
        super.applyMetaToStack();
    }
}