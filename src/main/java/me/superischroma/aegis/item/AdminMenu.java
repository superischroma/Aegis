package me.superischroma.aegis.item;

import me.superischroma.aegis.gui.AdminGUI;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.user.User;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class AdminMenu extends AegisItem
{
    public AdminMenu()
    {
        super("Admin Menu " + ChatColor.GRAY + "(Right Click)", Material.REDSTONE, Rarity.SPECIAL);
        super.glow();
        super.applyMetaToStack();
    }

    @Override
    public void onItemInteract(PlayerInteractEvent e)
    {
        e.setCancelled(true);
        Player player = e.getPlayer();
        if (!User.getUser(player).getRank().isAtLeast(Rank.BRUH))
        {
            player.sendMessage(ChatColor.RED + "This item is off-limits.");
            return;
        }
        new AdminGUI().open(player);
    }
}
