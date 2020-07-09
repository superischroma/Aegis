package me.superischroma.aegis.service;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.config.ConfigEntry;
import me.superischroma.aegis.sidebar.Sidebar;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.*;

public class SidebarManager extends AegisService
{
    private static String line = "&r&7&m                              ";
    private static String bottom = "&7&m                              ";

    private ScoreboardManager manager;
    private BukkitTask task;

    @Override
    public void start()
    {
        this.manager = Bukkit.getScoreboardManager();
        this.task = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (User user : User.getOnlineUsers())
                {
                    Player player = user.getPlayer();
                    Sidebar sb = new Sidebar("" + ChatColor.AQUA + ChatColor.BOLD + tl("%plugin.name%"), player.getName().toLowerCase());
                    sb.add(AUtil.color(line), 8);
                    if (ConfigEntry.REAL_TIME_ENABLED.getBoolean())
                    {
                        sb.add(ChatColor.GRAY + " " + AUtil.getTimeState(), 7);
                        sb.add(ChatColor.GRAY + " " + AUtil.getCurrentDate(), 6);
                        sb.add(ChatColor.GRAY + " " + AUtil.getCurrentTime(), 5);
                        sb.add("", 4);
                    }
                    Biome biome = player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
                    sb.add(ChatColor.GRAY + " Coins: " + ChatColor.GOLD + AUtil.getCommaSpacedLong(user.getCoins()), 3);
                    sb.add(" ", 2);
                    sb.add(AUtil.getBiomeColor(biome) + " » " + AUtil.getBiome(biome), 1);
                    sb.add(AUtil.color(bottom), 0);
                    sb.apply(player);
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }

    @Override
    public void stop()
    {

    }
}