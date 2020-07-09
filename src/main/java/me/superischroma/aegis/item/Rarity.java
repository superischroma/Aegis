package me.superischroma.aegis.item;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Rarity
{
    COMMON(ChatColor.WHITE),
    UNCOMMON(ChatColor.GREEN),
    RARE(ChatColor.BLUE),
    EPIC(ChatColor.DARK_PURPLE),
    LEGENDARY(ChatColor.GOLD),
    SPECIAL(ChatColor.LIGHT_PURPLE);

    @Getter
    private final ChatColor color;

    Rarity(ChatColor color)
    {
        this.color = color;
    }

    public String getName()
    {
        return "" + color + ChatColor.BOLD + name();
    }
}