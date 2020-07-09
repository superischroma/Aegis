package me.superischroma.aegis.item;

import org.bukkit.Material;

public class FuseSoul extends AegisItem
{
    public FuseSoul()
    {
        super("Fuse Soul", Material.GUNPOWDER, Rarity.UNCOMMON);
        super.glow();
        super.applyMetaToStack();
    }
}
