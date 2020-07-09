package me.superischroma.aegis.rank;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

import java.awt.*;

public enum Rank
{
    DEFAULT("default", false, new Color(121, 197, 160)),
    BRUH("bruh", true, new Color(255, 209, 222)),
    SUPER("super", true, new Color(255, 153, 148)),
    GOD("literally god", true, new Color(228, 220, 136));

    @Getter
    private final String name;

    @Getter
    private final boolean elevated;

    @Getter
    private final ChatColor color;

    Rank(String name, boolean elevated, Color color)
    {
        this.name = name;
        this.elevated = elevated;
        this.color = ChatColor.of(color);
    }

    public int getLevel()
    {
        return ordinal();
    }

    public boolean isAtLeast(Rank rank)
    {
        return getLevel() >= rank.getLevel();
    }

    public static Rank findRank(String rank)
    {
        return valueOf(rank.toUpperCase());
    }
}