package me.superischroma.aegis.item;

import me.superischroma.aegis.service.AegisService;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class AegisItemHandler extends AegisService
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
    public void onEntityInteract(PlayerInteractEntityEvent e)
    {
        for (AegisItemType type : AegisItemType.values())
        {
            if (AegisItem.isValid(e.getPlayer().getInventory().getItemInMainHand(), type) ||
                    AegisItem.isValid(e.getPlayer().getInventory().getItemInOffHand(), type))
            {
                AegisItem item = type.newInstance();
                if (item == null)
                    continue;
                item.onPlayerInteractEntity(e);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e)
    {
        ItemStack stack = e.getItem();
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK ||
            e.getAction() == Action.RIGHT_CLICK_AIR) &&
            !AUtil.isStackAir(stack))
        {
            for (AegisItemType type : AegisItemType.values())
            {
                if (AegisItem.isValid(stack, type))
                {
                    AegisItem item = type.newInstance();
                    if (item == null)
                        continue;
                    item.onItemInteract(e);
                }
            }
        }
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        if (e.getClickedBlock() == null)
            return;
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        for (String key : plugin.blocks.getKeys(false))
        {
            List<Location> locations = (List<Location>) plugin.blocks.getList(key);
            if (locations.contains(block.getLocation()))
            {
                AegisItem item = AegisItemType.findType(key).newInstance();
                if (item == null)
                    continue;
                item.onBlockInteract(e);
                if (item.hasGUI())
                {
                    e.setCancelled(true);
                    item.getGUI().open(player);
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