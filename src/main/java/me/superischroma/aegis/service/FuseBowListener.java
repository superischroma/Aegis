package me.superischroma.aegis.service;

import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.AegisItemType;
import me.superischroma.aegis.util.ALog;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuseBowListener extends AegisService
{
    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
    }

    private static Map<Arrow, Float> arrows = new HashMap<>();

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent e)
    {
        Entity proj = e.getProjectile();
        if (!(proj instanceof Arrow))
        {
            return;
        }

        ItemStack bow = e.getBow();

        if (bow == null)
        {
            return;
        }

        if (!bow.hasItemMeta())
        {
            return;
        }

        if (!bow.getItemMeta().hasDisplayName())
        {
            return;
        }

        if (AegisItem.isValid(bow, AegisItemType.FUSE_BOW))
        {
            arrows.put((Arrow) proj, (e.getForce() * 10.0f) / 2.0f);
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e)
    {
        Projectile proj = e.getEntity();
        if (!(proj instanceof Arrow))
        {
            return;
        }

        Arrow arrow = (Arrow) proj;

        for (Arrow a : arrows.keySet())
        {
            if (arrow == a)
            {
                Block block = e.getHitBlock();

                if (block == null)
                {
                    return;
                }

                block.getWorld().strikeLightning(block.getLocation());
                block.getWorld().createExplosion(block.getLocation(), arrows.get(a));
                arrows.remove(a);
                a.remove();
                return;
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e)
    {
        Entity damager = e.getDamager();
        if (!(damager instanceof Arrow))
        {
            return;
        }

        Arrow arrow = (Arrow) damager;
        for (Arrow a : arrows.keySet())
        {
            if (arrow == a)
            {
                Entity damaged = e.getEntity();
                if (damaged.isDead())
                {
                    return;
                }

                damaged.getWorld().strikeLightning(damaged.getLocation());
                damaged.getWorld().createExplosion(damaged.getLocation(), 4f);
                arrows.remove(a);
                return;
            }
        }
    }
}