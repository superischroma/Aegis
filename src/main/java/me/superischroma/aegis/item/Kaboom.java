package me.superischroma.aegis.item;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Kaboom extends AegisItem
{
    public Kaboom()
    {
        super("Kaboom", Material.BEACON, Rarity.SPECIAL);
        super.glow();
        super.applyMetaToStack();
    }

    @Override
    public void onBlockInteract(PlayerInteractEvent e)
    {
        e.setCancelled(true);
        Player player = e.getPlayer();
        for (Entity entity : player.getNearbyEntities(50, 50, 50))
        {
            if (entity == player)
                continue;
            entity.getWorld().strikeLightning(entity.getLocation());
            entity.setVelocity(entity.getVelocity().clone().add(new Vector(0, 50, 0)));
        }
    }
}
