package me.superischroma.aegis.item;

import lombok.Getter;
import org.bukkit.inventory.PlayerInventory;

public enum ArmorSet
{
    GODS(AegisItemType.GODS_HELMET, AegisItemType.GODS_CHESTPLATE, AegisItemType.GODS_LEGGINGS, AegisItemType.GODS_BOOTS);

    @Getter
    private final AegisItemType helmet;
    @Getter
    private final AegisItemType chestplate;
    @Getter
    private final AegisItemType leggings;
    @Getter
    private final AegisItemType boots;

    ArmorSet(AegisItemType helmet, AegisItemType chestplate, AegisItemType leggings, AegisItemType boots)
    {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public boolean hasFullSetEquipped(PlayerInventory inv)
    {
        if (inv.getHelmet() == null ||
                inv.getChestplate() == null ||
                inv.getLeggings() == null ||
                inv.getBoots() == null)
            return false;
        return AegisItem.isValid(inv.getHelmet(), helmet) &&
                AegisItem.isValid(inv.getChestplate(), chestplate) &&
                AegisItem.isValid(inv.getLeggings(), leggings) &&
                AegisItem.isValid(inv.getBoots(), boots);
    }
}