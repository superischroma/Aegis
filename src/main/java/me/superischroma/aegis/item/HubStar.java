package me.superischroma.aegis.item;

import me.superischroma.aegis.gui.HubGUI;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class HubStar extends AegisItem
{
    public HubStar()
    {
        super("Hub Star", Material.NETHER_STAR, Rarity.COMMON);
        super.glow();
        super.applyMetaToStack();
    }

    @Override
    public void onItemInteract(PlayerInteractEvent e)
    {
        e.setCancelled(true);
        new HubGUI().open(e.getPlayer());
    }
}
