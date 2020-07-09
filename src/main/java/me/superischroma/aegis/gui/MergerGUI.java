package me.superischroma.aegis.gui;

import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.AegisItemType;
import me.superischroma.aegis.item.Recipe;
import me.superischroma.aegis.util.ALog;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class MergerGUI extends GUI
{
    private static List<Integer> blank = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18,
            22, 24, 26, 27, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44);
    private static List<Integer> itemSlots = Arrays.asList(10, 11, 12, 19, 20, 21, 28, 29, 30);

    private static ItemStack productInvalid = AUtil.createNamedStack(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "The product will appear here!");

    public MergerGUI()
    {
        super("Merger", 45);
        for (int b : blank)
            super.setItem(b, AUtil.getBlank());
        super.setItem(23, AUtil.createNamedStack(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GOLD + "Merge!"));
    }

    @Override
    public void onClick(InventoryClickEvent e)
    {
        int slot = e.getRawSlot();
        Inventory inv = e.getClickedInventory();
        if (inv == null)
            return;
        if (blank.contains(slot) || slot == 23)
        {
            e.setCancelled(true);
        }
        if (slot == 23)
        {
            for (AegisItemType type : AegisItemType.values())
            {
                boolean cycle = false;
                AegisItem item = type.newInstance();
                if (item == null)
                    continue;
                if (!item.hasRecipe())
                    continue;
                Recipe recipe = item.getRecipe();
                List<ItemStack> recipeStacks = recipe.getStackList();
                for (int i = 0; i < 9; i++)
                {
                    ItemStack currentSlot = inv.getItem(itemSlots.get(i));
                    if (currentSlot == null || currentSlot.isSimilar(new ItemStack(Material.AIR)))
                    {
                        if (!recipeStacks.get(i).isSimilar(new ItemStack(Material.AIR)))
                        {
                            cycle = true;
                            break;
                        }
                    }
                    else
                    {
                        if (!recipeStacks.get(i).isSimilar(currentSlot))
                        {
                            cycle = true;
                            break;
                        }
                        if (recipeStacks.get(i).getAmount() != currentSlot.getAmount())
                        {
                            cycle = true;
                            break;
                        }
                    }
                }
                if (cycle)
                    continue;
                ItemStack fin = item.getStack().clone();
                ItemStack res = inv.getItem(25);
                if (res != null)
                {
                    if (!res.isSimilar(new ItemStack(Material.AIR)))
                    {
                        if (!item.getStack().isSimilar(res))
                            continue;
                        ItemStack newRes = res.clone();
                        newRes.setAmount(res.getAmount() + 1);
                        fin = newRes;
                    }
                }
                if (cycle)
                    continue;
                for (int i = 0; i < 9; i++)
                {
                    int itemSlot = itemSlots.get(i);
                    ItemStack currentStack = inv.getItem(itemSlots.get(i));
                    if (currentStack == null)
                    {
                        inv.setItem(itemSlot, null);
                        continue;
                    }
                    int newAmount = currentStack.getAmount() - recipe.getAmountOfOriginalStack(i);
                    if (newAmount <= 0)
                    {
                        inv.setItem(itemSlot, null);
                        continue;
                    }
                    currentStack.setAmount(newAmount);
                    inv.setItem(itemSlot, currentStack);
                }
                inv.setItem(25, fin);
            }
        }
    }
}