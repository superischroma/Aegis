package me.superischroma.aegis.gui;

import me.superischroma.aegis.item.AegisInstanceItem;
import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.user.User;
import me.superischroma.aegis.util.ALog;
import me.superischroma.aegis.util.AUtil;
import me.superischroma.aegis.util.CompactEnchantment;
import me.superischroma.aegis.util.ModifyableItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class EnchanterGUI extends GUI
{
    public EnchanterGUI()
    {
        super("Enchanter", 45);
        super.fill();
        super.setItem(19, null);
        super.setItem(22, AUtil.createNamedStack(Material.ANVIL, ChatColor.GREEN + "Combine!"));
        super.setItem(25, null);
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        int slot = e.getRawSlot();
        if (slot != 19 && slot != 25 && slot <= 45)
        {
            e.setCancelled(true);
        }
        if (slot != 22)
            return;
        ItemStack stack = inv.getItem(19);
        ItemStack bookStack = inv.getItem(25);
        if (AUtil.isStackAir(stack))
        {
            player.sendMessage(ChatColor.RED + "Nothing to apply to!");
            return;
        }
        if (AUtil.isStackAir(bookStack))
        {
            player.sendMessage(ChatColor.RED + "No book to apply!");
            return;
        }
        if (bookStack.getType() != Material.ENCHANTED_BOOK)
        {
            player.sendMessage(ChatColor.RED + "This is not an enchanted book!");
            return;
        }
        AegisItem from = AegisItem.from(stack);
        if (!from.isEnchantable())
        {
            player.sendMessage(ChatColor.RED + "That item is not enchantable.");
            return;
        }
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) bookStack.getItemMeta();
        AegisInstanceItem item = AegisInstanceItem.from(stack);
        if (meta == null || item == null)
        {
            player.sendMessage(ChatColor.RED + "Something went wrong while trying to enchant this item!");
            return;
        }
        boolean cancel = true;
        for (Enchantment enchantment : meta.getStoredEnchants().keySet())
        {
            if (!item.alreadyHasEnchantment(enchantment))
            {
                cancel = false;
                item.addEnchantment(enchantment, meta.getStoredEnchantLevel(enchantment));
            }
        }
        if (cancel)
        {
            player.sendMessage(ChatColor.RED + "Your item already has this/these enchantment(s)!");
            return;
        }
        ModifyableItemStack created = ModifyableItemStack.from(item.create());
        for (CompactEnchantment enchantment : item.getEnchantments())
        {
            created.addEnchant(enchantment.getEnchantment(), enchantment.getLevel());
        }
        created.apply();
        inv.setItem(19, created);
        inv.setItem(25, null);
        User.getUser(player).play(Sound.BLOCK_ANVIL_USE);
    }
}