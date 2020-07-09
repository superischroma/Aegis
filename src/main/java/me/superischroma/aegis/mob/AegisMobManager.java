package me.superischroma.aegis.mob;

import lombok.Getter;
import me.superischroma.aegis.service.AegisService;
import me.superischroma.aegis.util.RandomCollection;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AegisMobManager extends AegisService
{
    @Getter
    private List<AegisSpawnedMob> mobs;

    private BukkitTask task;

    @Override
    public void start()
    {
        mobs = new ArrayList<>();
        task = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Iterator<AegisSpawnedMob> iter = mobs.iterator();
                while (iter.hasNext())
                {
                    AegisSpawnedMob mob = iter.next();
                    mob.refreshTag();
                    if (!mob.getEntity().getWorld().getEntities().contains(mob.getEntity()))
                    {
                        mob.kill();
                        iter.remove();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void stop()
    {
        killMobs();
        mobs.clear();
        task.cancel();
        mobs = null;
        task = null;
    }

    public void addMob(AegisSpawnedMob mob)
    {
        mobs.add(mob);
    }

    public void removeMob(AegisSpawnedMob mob)
    {
        mobs.remove(mob);
    }

    public void killMobs()
    {
        Iterator<AegisSpawnedMob> iter = mobs.iterator();
        while (iter.hasNext())
        {
            iter.next().kill();
            iter.remove();
        }
    }

    public AegisSpawnedMob getAegisMob(Entity entity)
    {
        for (AegisSpawnedMob mob : mobs)
        {
            if (mob.getEntity() == entity)
                return mob;
        }
        return null;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e)
    {
        Entity entity = e.getEntity();
        AegisSpawnedMob mob = getAegisMob(entity);
        if (mob == null)
            return;
        mob.kill();
        removeMob(mob);
        RandomCollection<ItemStack> drops = mob.getBase().getDrops();
        if (drops.size() != 0)
        {
            e.getDrops().clear();
            e.getDrops().add(mob.getBase().getDrops().next());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        Entity entity = e.getEntity();
        AegisSpawnedMob mob = getAegisMob(entity);
        if (mob == null)
            return;
        mob.setHealth(((Damageable) entity).getHealth());
    }
}