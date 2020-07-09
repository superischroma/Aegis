package me.superischroma.aegis.gui;

import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HubGUI extends GUI
{
    public HubGUI()
    {
        super("Hub", 27);
        super.fill();
        super.setItem(14, AUtil.createNamedStack(Material.CRAFTING_TABLE, ChatColor.GOLD + "Open Merger"));
        super.setItem(15, AUtil.createNamedStack(Material.BLAST_FURNACE, ChatColor.LIGHT_PURPLE + "Open Reinforcer"));
        super.setItem(16, AUtil.createNamedStack(Material.FURNACE, ChatColor.GRAY + "Open Crusher"));
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();
        switch (slot)
        {
            case 14:
            {
                new MergerGUI().open(player);
                break;
            }
            case 15:
            {
                new ReinforcerGUI().open(player);
                break;
            }
            case 16:
            {
                new CrusherGUI().open(player);
                break;
            }
        }
    }
}