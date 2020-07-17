package me.superischroma.aegis.item;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class MagicalSaddle extends AegisItem
{
    public MagicalSaddle()
    {
        super("Magical Saddle", Material.SADDLE, Rarity.EPIC);
        super.glow();
        super.applyMetaToStack();
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e)
    {
        e.setCancelled(true);
        e.getRightClicked().addPassenger(e.getPlayer());
    }
}
