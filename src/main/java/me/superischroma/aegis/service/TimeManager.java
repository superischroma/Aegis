package me.superischroma.aegis.service;

import me.superischroma.aegis.config.ConfigEntry;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Calendar;

public class TimeManager extends AegisService
{
    private BukkitTask task;

    @Override
    public void start()
    {
        World world = Bukkit.getWorld(ConfigEntry.REAL_TIME_WORLD.getString());
        if (ConfigEntry.REAL_TIME_ENABLED.getBoolean())
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        this.task = new BukkitRunnable()
        {
            public void run()
            {
                if (!ConfigEntry.REAL_TIME_ENABLED.getBoolean())
                    return;
                if (world == null)
                    return;
                world.setTime(getTicksFromRealTime());
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    @Override
    public void stop()
    {
        this.task.cancel();
        this.task = null;
    }

    public int getTicksFromRealTime()
    {
        double ticks = 0;
        Calendar c = Calendar.getInstance();
        double hour = c.get(Calendar.HOUR_OF_DAY);
        double minute = c.get(Calendar.MINUTE);
        ticks += (hour * 1000) - 6000;
        ticks += minute * 16.6666666667;
        return (int) ticks;
    }
}