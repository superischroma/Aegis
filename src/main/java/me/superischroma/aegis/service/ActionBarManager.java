package me.superischroma.aegis.service;

import me.superischroma.aegis.user.User;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class ActionBarManager extends AegisService
{
    private BukkitTask task;

    @Override
    public void start()
    {
        this.task = new BukkitRunnable()
        {
            public void run()
            {
                for (User user : User.getOnlineUsers())
                {
                    Player player = user.getPlayer();
                    double health;
                    if (!plugin.puh.hasHealth(player))
                        health = user.getMaxHealth();
                    else
                        health = Math.ceil(plugin.puh.getHealth(player));
                    user.sendActionBar(ChatColor.RED + "" + ((int) health) + "/" + ((int) user.getMaxHealth()) + "❤         " + ChatColor.GREEN + ((int) user.getDefense()) + "⛌");
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }

    @Override
    public void stop()
    {
        this.task.cancel();
        this.task = null;
    }
}