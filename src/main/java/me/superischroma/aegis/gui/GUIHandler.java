package me.superischroma.aegis.gui;

import me.superischroma.aegis.service.AegisService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIHandler extends AegisService
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
    public void onInventoryClick(InventoryClickEvent e)
    {
        for (GUIType type : GUIType.values())
        {
            GUI gui;
            try
            {
                gui = type.getGUIClass().newInstance();
            }
            catch (InstantiationException | IllegalAccessException ex)
            {
                continue;
            }
            if (e.getView().getTitle().equals(gui.getName()))
            {
                gui.onClick(e);
            }
        }
    }
}