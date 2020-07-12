package me.superischroma.aegis.user;

import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.Weapon;
import me.superischroma.aegis.service.AegisService;
import me.superischroma.aegis.util.ALog;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.PlayerInventory;

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
        ALog.info("br");
        Player player = (Player) e.getDamager();
        PlayerInventory inv = player.getInventory();
        AegisItem used = null;
        AegisItem mheld = AegisItem.from(inv.getItemInMainHand());
        ALog.info(mheld);
        AegisItem oheld = AegisItem.from(inv.getItemInOffHand());
        ALog.info(oheld);
        if (mheld != null)
            used = mheld;
        if (oheld != null)
            used = oheld;
        if (used == null)
            return;
        if (!(used instanceof Weapon))
            return;
        Weapon weapon = (Weapon) used;
        ALog.info(weapon.getName());
        e.setDamage((5 + weapon.getDamage() + (weapon.getStrength() / 5.0)) * (1 + (weapon.getStrength() / 100.0)));
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e)
    {
        if (!(e.getEntity() instanceof Player))
            return;
        User user = User.getUser((Player) e.getEntity());
        Player player = user.getPlayer();
        double damage = e.getDamage();
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