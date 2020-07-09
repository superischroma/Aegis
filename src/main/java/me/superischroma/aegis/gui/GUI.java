package me.superischroma.aegis.gui;

import lombok.Getter;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class GUI
{
    // name of the gui
    @Getter
    private String name;

    // slot count
    private int slots;

    // its contents
    private List<ItemStack> contents;

    // the actual inventory
    @Getter
    private Inventory gui;

    public GUI(String name, int slots)
    {
        this.name = name;
        this.slots = slots;
        this.contents = new ArrayList<>();
        this.gui = Bukkit.createInventory(null, slots, name);
    }

    public void fill()
    {
        for (int i = 0; i < slots; i++)
        {
            gui.setItem(i, AUtil.getBlank());
        }
    }

    public void setItem(int i, ItemStack stack)
    {
        contents.add(stack);
        gui.setItem(i, stack);
    }

    public void open(Player player)
    {
        player.openInventory(gui);
    }

    public abstract void onClick(InventoryClickEvent e);
}