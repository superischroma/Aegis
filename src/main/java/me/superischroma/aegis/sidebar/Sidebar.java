package me.superischroma.aegis.sidebar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.List;

public class Sidebar
{
    private static ScoreboardManager manager = Bukkit.getScoreboardManager();

    private String name;
    private String identifier;

    private Scoreboard board;
    private Objective obj;
    private List<Score> scores;

    public Sidebar(String name, String identifier)
    {
        this.name = name;
        this.identifier = identifier;
        this.board = manager.getNewScoreboard();
        this.obj = board.registerNewObjective(identifier, "");
        this.scores = new ArrayList<>();
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(name);
    }

    public void add(String s, int pos)
    {
        Score score = obj.getScore(s);
        score.setScore(pos);
        scores.add(score);
    }

    public void apply(Player player)
    {
        player.setScoreboard(board);
    }
}