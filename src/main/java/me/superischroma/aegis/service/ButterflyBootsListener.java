package me.superischroma.aegis.service;

import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.AegisItemType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.PlayerInventory;

public class ButterflyBootsListener extends AegisService
{
    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e)
    {
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        Player player = (Player) e.getEntity();
        PlayerInventory inv = player.getInventory();
        if (inv.getBoots() == null)
            return;
        if (!AegisItem.isValid(inv.getBoots(), AegisItemType.BUTTERFLY_BOOTS))
            return;
        e.setDamage(e.getDamage() / 8.0);
    }
}