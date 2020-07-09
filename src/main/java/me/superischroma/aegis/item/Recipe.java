package me.superischroma.aegis.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Recipe
{
    private Map<String, ItemStack> matcher;
    private String pattern;

    public Recipe(String line1, String line2, String line3)
    {
        this.matcher = new HashMap<>();
        this.pattern = line1 + line2 + line3;
    }

    public void setIngredient(char c, ItemStack stack)
    {
        matcher.put("" + c, stack);
    }

    public void setIngredient(char c, AegisItem item)
    {
        setIngredient(c, item.getStack());
    }

    public void setIngredient(char c, Material material)
    {
        setIngredient(c, new ItemStack(material));
    }

    public List<ItemStack> getStackList()
    {
        List<ItemStack> stacks = new ArrayList<>();
        String[] sp = pattern.split("");
        for (String s : sp)
        {
            if (s.equals(" "))
                stacks.add(new ItemStack(Material.AIR));
            else
                stacks.add(matcher.get(s));
        }
        return stacks;
    }

    public int getAmountOfOriginalStack(int index)
    {
        String[] sp = pattern.split("");
        ItemStack stack = matcher.get(sp[index]);
        if (stack == null)
        {
            return 0;
        }
        return stack.getAmount();
    }
}