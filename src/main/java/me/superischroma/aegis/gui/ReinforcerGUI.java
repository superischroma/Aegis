package me.superischroma.aegis.gui;

import me.superischroma.aegis.item.*;
import me.superischroma.aegis.util.AUtil;
import me.superischroma.aegis.util.ModifyableItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class ReinforcerGUI extends GUI
{
    public ReinforcerGUI()
    {
        super("Reinforcer", 27);
        super.fill();
        super.setItem(11, null);
        super.setItem(15, AUtil.createNamedStack(Material.ANVIL, ChatColor.LIGHT_PURPLE + "Reinforce!"));
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getClickedInventory();
        if (inv == null)
            return;
        int slot = e.getRawSlot();
        if (slot != 11 && slot <= 27)
        {
            e.setCancelled(true);
        }
        if (slot == 15)
        {
            ModifyableItemStack toReinforce = ModifyableItemStack.from(e.getClickedInventory().getItem(11));
            if (AUtil.isStackAir(toReinforce))
            {
                player.sendMessage(ChatColor.RED + "No item to reinforce!");
                return;
            }
            if (AegisItem.from(toReinforce).getType() == ItemType.NONE)
            {
                player.sendMessage(ChatColor.RED + "This item cannot be reinforced!");
                return;
            }
            PlayerInventory pinv = player.getInventory();
            ItemStack stone = null;
            for (ItemStack stack : pinv.getContents())
            {
                if (AUtil.singularify(stack).isSimilar(new ReinforcerStone().getStack()))
                {
                    stone = stack;
                }
            }
            if (stone == null)
            {
                player.sendMessage(ChatColor.RED + "You don't have any reinforcer stones!");
                return;
            }
            stone.setAmount(stone.getAmount() - 1);
            AegisInstanceItem aii = AegisInstanceItem.from(toReinforce);
            aii.setVariant(AUtil.getRandomVariant(aii.getItem().getType()));
            inv.setItem(11, aii.create());
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
        }
    }
}