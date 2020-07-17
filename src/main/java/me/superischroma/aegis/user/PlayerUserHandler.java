package me.superischroma.aegis.user;

import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.Armor;
import me.superischroma.aegis.item.Weapon;
import me.superischroma.aegis.mob.AegisSpawnedMob;
import me.superischroma.aegis.service.AegisService;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PlayerUserHandler extends AegisService
{
    public Map<Player, Double> HEALTH;

    @Override
    public void start()
    {
        HEALTH = new HashMap<>();
    }

    @Override
    public void stop()
    {
        HEALTH.clear();
        HEALTH = null;
    }

    public boolean hasHealth(Player player)
    {
        return HEALTH.containsKey(player);
    }

    public double getHealth(Player player)
    {
        return HEALTH.get(player);
    }

    public void setHealth(Player player, double health)
    {
        HEALTH.remove(player);
        HEALTH.put(player, health);
    }

    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent e)
    {
        if (!(e.getDamager() instanceof Player))
            return;
        Player player = (Player) e.getDamager();
        Entity entity = e.getEntity();
        PlayerInventory inv = player.getInventory();
        AegisItem used = null;
        AegisItem mheld = AegisItem.from(inv.getItemInMainHand());
        AegisItem oheld = AegisItem.from(inv.getItemInOffHand());
        if (mheld != null)
            used = mheld;
        if (oheld != null)
            used = oheld;
        if (used == null)
            return;
        if (!(used instanceof Weapon))
            return;
        Weapon weapon = (Weapon) used;
        double damage = (5 + weapon.getDamage() + (weapon.getStrength() / 5.0)) * (1 + (weapon.getStrength() / 100.0));
        e.setDamage(damage);
        ArmorStand stand = (ArmorStand) entity.getWorld().spawnEntity(
                entity.getLocation().clone().add(
                        AUtil.getRandomDouble(-1.0, 1.0),
                        AUtil.getRandomDouble(1.0, 2.0),
                        AUtil.getRandomDouble(-1.0, 1.0)),
                EntityType.ARMOR_STAND);
        stand.setAI(false);
        stand.setInvulnerable(true);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setCustomName(AUtil.getReadableRandomChatColor() + "☆" + ((int) damage) + "☆");
        stand.setCustomNameVisible(true);
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                stand.remove();
            }
        }.runTaskLater(plugin, 30);
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;
        User user = User.getUser((Player) e.getEntity());
        Player player = user.getPlayer();
        double damage = e.getDamage();
        if (e instanceof EntityDamageByEntityEvent)
        {
            EntityDamageByEntityEvent ee = (EntityDamageByEntityEvent) e;
            if (!(ee.getDamager() instanceof Player))
            {
                AegisSpawnedMob mob = plugin.amm.getAegisMob(ee.getDamager());
                if (mob == null)
                    return;
                user = User.getUser((Player) ee.getEntity());
                damage = mob.getBase().getStrength();
            }
        }
        player.setAbsorptionAmount(Double.MAX_VALUE);
        e.setDamage(0.0001);
        if (hasHealth(player))
        {
            double health = getHealth(player);
            setHealth(player, health - (damage * (1.00 - (user.getDefense() / (user.getDefense() + 100)))));
            if (getHealth(player) <= 0.0)
            {
                user.getPlayer().setHealth(0.0);
                setHealth(player, user.getMaxHealth());
            }
        }
        else
        {
            HEALTH.put(player, user.getMaxHealth() - (damage * (1.00 - (user.getDefense() / (user.getDefense() + 100)))));
        }
    }
}