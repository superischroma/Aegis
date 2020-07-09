package me.superischroma.aegis.gui;

import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.AegisItemType;
import me.superischroma.aegis.item.ReinforcerStone;
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
    private static List<AegisItemType> reinforceable = Arrays.asList(AegisItemType.FUSE_BOW, AegisItemType.BUTTERFLY_BOOTS);

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
            boolean able = false;
            for (AegisItemType type : reinforceable)
            {
                if (AegisItem.isValid(toReinforce, type))
                {
                    able = true;
                }
            }
            if (!able)
            {
                player.sendMessage(ChatColor.RED + "This item cannot be reinforced!");
                return;
            }
            if (toReinforce.getName().contains("Reinforced"))
            {
                player.sendMessage(ChatColor.RED + "That item is already reinforced!");
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
            ModifyableItemStack reinforced = ModifyableItemStack.from(toReinforce.clone());
            String name = reinforced.getName();
            ChatColor color = ChatColor.WHITE;
            if (name.startsWith("§"))
            {
                color = ChatColor.getByChar(name.substring(1, 2));
            }
            stone.setAmount(stone.getAmount() - 1);
            reinforced.setName(color + "Reinforced " + reinforced.getName());
            reinforced.addAttribute(Attribute.GENERIC_MOVEMENT_SPEED, 0.1);
            reinforced.addAttribute(Attribute.GENERIC_ATTACK_DAMAGE, 0.5);
            reinforced.apply();
            inv.setItem(11, reinforced);
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0F, 1.0F);
        }
    }
}