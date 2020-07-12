package me.superischroma.aegis.gui;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AdminGUI extends GUI
{
    public AdminGUI()
    {
        super("Admin", 45);
        super.fill();
        super.setItem(10, AUtil.createNamedStack(Material.IRON_SWORD, ChatColor.RED + "Survival"));
        super.setItem(11, AUtil.createNamedStack(Material.GOLDEN_APPLE, ChatColor.LIGHT_PURPLE + "Heal"));
        super.setItem(19, AUtil.createNamedStack(Material.DIAMOND_BLOCK, ChatColor.GREEN + "Creative"));
        super.setItem(20, AUtil.createNamedStack(Material.COOKED_BEEF, ChatColor.GOLD + "Feed"));
        super.setItem(28, AUtil.createNamedStack(Material.BARRIER, ChatColor.GRAY + "Spectator"));
        super.setItem(29, AUtil.createNamedStack(Material.ENCHANTED_GOLDEN_APPLE, ChatColor.DARK_AQUA + "Heal + Feed"));
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();
        switch (slot)
        {
            case 10:
            {
                player.setGameMode(GameMode.SURVIVAL);
                break;
            }
            case 11:
            {
                plugin.puh.setHealth(player, User.getUser(player).getMaxHealth());
                break;
            }
            case 19:
            {
                player.setGameMode(GameMode.CREATIVE);
                break;
            }
            case 20:
            {
                player.setFoodLevel(20);
                player.setSaturation(20.0f);
                break;
            }
            case 28:
            {
                player.setGameMode(GameMode.SPECTATOR);
                break;
            }
            case 29:
            {
                plugin.puh.setHealth(player, User.getUser(player).getMaxHealth());
                player.setFoodLevel(20);
                player.setSaturation(20.0f);
                break;
            }
            default: return;
        }
        player.closeInventory();
    }
}