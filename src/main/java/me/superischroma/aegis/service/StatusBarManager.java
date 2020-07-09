package me.superischroma.aegis.service;

import me.superischroma.aegis.User;
import me.superischroma.aegis.config.ConfigEntry;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Calendar;

public class StatusBarManager extends AegisService
{
    private BukkitTask task;

    @Override
    public void start()
    {
        this.task = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                for (User user : User.getOnlineUsers())
                {
                    if (!user.hasStatusBarEnabled())
                        continue;
                    if (!ConfigEntry.REAL_TIME_ENABLED.getBoolean())
                        continue;
                    String message = "";
                    if (ConfigEntry.REAL_TIME_ENABLED.getBoolean())
                    {
                        Calendar c = Calendar.getInstance();
                        message += ChatColor.GOLD + "" + c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + " " +
                                (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
                    }
                    user.sendActionBar(message);
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void stop()
    {
        this.task.cancel();
        this.task = null;
    }
}