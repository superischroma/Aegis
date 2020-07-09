package me.superischroma.aegis.gui;

import me.superischroma.aegis.item.CrushedDiamond;
import me.superischroma.aegis.item.CrushedGold;
import me.superischroma.aegis.item.CrushedIron;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CrusherGUI extends GUI
{
    private static List<Material> crushable = Arrays.asList(Material.IRON_INGOT, Material.GOLD_INGOT, Material.DIAMOND);

    public CrusherGUI()
    {
        super("Crusher", 27);
        super.fill();
        super.setItem(11, null);
        super.setItem(15, AUtil.createNamedStack(Material.NETHERITE_BLOCK, ChatColor.GRAY + "Crush!"));
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        Inventory inv = e.getClickedInventory();
        Player player = (Player) e.getWhoClicked();
        if (inv == null)
            return;
        int slot = e.getRawSlot();
        if (slot != 11 && slot <= 27)
        {
            e.setCancelled(true);
        }
        if (slot == 15)
        {
            ItemStack stack = inv.getItem(11);
            if (AUtil.isStackAir(stack))
            {
                player.sendMessage(ChatColor.RED + "Put in an item to crush!");
                return;
            }
            if (!crushable.contains(stack.getType()))
            {
                player.sendMessage(ChatColor.RED + "That item is not crushable!");
                return;
            }
            if (stack.hasItemMeta())
            {
                if (stack.getItemMeta().hasDisplayName())
                {
                    player.sendMessage(ChatColor.RED + "That item cannot be crushed!");
                    return;
                }
            }
            int amount = stack.getAmount();
            switch (stack.getType())
            {
                case DIAMOND:
                {
                    inv.setItem(11, AUtil.amount(new CrushedDiamond().getStack(), amount));
                    break;
                }
                case GOLD_INGOT:
                {
                    inv.setItem(11, AUtil.amount(new CrushedGold().getStack(), amount));
                    break;
                }
                case IRON_INGOT:
                {
                    inv.setItem(11, AUtil.amount(new CrushedIron().getStack(), amount));
                    break;
                }
            }
            player.playSound(player.getLocation(), Sound.BLOCK_NETHERITE_BLOCK_PLACE, 1.0F, 1.0F);
        }
    }
}