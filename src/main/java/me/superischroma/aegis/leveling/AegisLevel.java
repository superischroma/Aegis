package me.superischroma.aegis.leveling;

import lombok.Getter;
import lombok.Setter;

public class AegisLevel
{
    @Getter
    @Setter
    private int level;

    @Getter
    @Setter
    private double progress;

    @Getter
    @Setter
    private int nextLevel;

    public AegisLevel(int level, double progress, int nextLevel)
    {
        this.level = level;
        this.progress = progress;
        this.nextLevel = nextLevel;
    }
}