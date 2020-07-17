package me.superischroma.aegis.service;

import me.superischroma.aegis.item.Nuke;
import me.superischroma.aegis.item.WaterBalloon;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.Iterator;

public class ImpactHandler extends AegisService
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
                Iterator<ArmorStand> iter = WaterBalloon.getThrownWaterBalloons().iterator();
                Iterator<ArmorStand> nukeiter = Nuke.getThrownNukes().iterator();
                while (iter.hasNext())
                {
                    ArmorStand balloon = iter.next();
                    if (balloon.getLocation().subtract(new Vector(0, 1, 0)).getBlock().getType() != Material.AIR)
                    {
                        iter.remove();
                        balloon.getLocation().getBlock().setType(Material.WATER);
                        balloon.remove();
                    }
                }
                while (nukeiter.hasNext())
                {
                    ArmorStand nuke = nukeiter.next();
                    if (nuke.getLocation().subtract(new Vector(0, 1, 0)).getBlock().getType() != Material.AIR)
                    {
                        nukeiter.remove();
                        nuke.getWorld().createExplosion(nuke.getLocation(), 32F);
                        nuke.remove();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 5);
    }

    @Override
    public void stop()
    {
        this.task.cancel();
        this.task = null;
    }
}