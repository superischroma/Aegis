package me.superischroma.aegis.item;

import me.superischroma.aegis.service.AegisService;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class AegisBlockHandler extends AegisService
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
    public void onBlockPlace(BlockPlaceEvent e)
    {
        for (AegisItemType type : AegisItemType.values())
        {
            if (AegisItem.isValid(e.getItemInHand(), type))
            {
                List<Location> locations = (List<Location>) plugin.blocks.getList(type.name().toLowerCase());
                if (locations == null)
                {
                    locations = new ArrayList<>();
                }
                locations.add(e.getBlockPlaced().getLocation());
                plugin.blocks.set(type.name().toLowerCase(), locations);
                plugin.blocks.save();
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        for (String key : plugin.blocks.getKeys(false))
        {
            List<Location> locations = (List<Location>) plugin.blocks.getList(key);
            if (locations.contains(e.getBlock().getLocation()))
            {
                e.setCancelled(true);
                handleBlockDestruction(e, key, locations, e.getPlayer().getGameMode() != GameMode.CREATIVE);
                return;
            }
        }
    }

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e)
    {
        for (String key : plugin.blocks.getKeys(false))
        {
            List<Location> locations = (List<Location>) plugin.blocks.getList(key);
            if (locations.contains(e.getBlock().getLocation()))
            {
                if (e.getChangedType() != Material.AIR)
                    return;
                e.setCancelled(true);
                handleBlockDestruction(e, key, locations, true);
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getClickedBlock() == null)
            return;
        for (String key : plugin.blocks.getKeys(false))
        {
            List<Location> locations = (List<Location>) plugin.blocks.getList(key);
            if (locations.contains(e.getClickedBlock().getLocation()))
            {
                AegisItem item = AegisItemType.findType(key).newInstance();
                if (item == null)
                    continue;
                item.onBlockInteract(e);
                if (item.hasGUI())
                {
                    e.setCancelled(true);
                    item.getGUI().open(e.getPlayer());
                }
                return;
            }
        }
    }

    private void handleBlockDestruction(BlockEvent e, String key, List<Location> locations, boolean drop)
    {
        locations.remove(e.getBlock().getLocation());
        plugin.blocks.set(key, locations);
        plugin.blocks.save();
        AegisItem item = null;
        for (AegisItemType type : AegisItemType.values())
        {
            if (key.equals(type.name().toLowerCase()))
            {
                item = type.newInstance();
            }
        }
        if (item == null)
            return;
        e.getBlock().setType(Material.AIR);
        e.getBlock().getDrops().clear();
        if (drop)
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), item.getStack());
    }
}