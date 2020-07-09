package me.superischroma.aegis.item;

import me.superischroma.aegis.service.AegisService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class CustomCrafting extends AegisService
{
    private NamespacedKey key;

    @Override
    public void start()
    {
        // merger
        this.key = new NamespacedKey(plugin, "merger");
        ShapedRecipe recipe = new ShapedRecipe(this.key, new Merger().getStack());
        recipe.shape("DED", "ERE", "DED");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('E', Material.EMERALD);
        recipe.setIngredient('R', Material.REDSTONE);
        Bukkit.addRecipe(recipe);
    }

    @Override
    public void stop()
    {
        Bukkit.removeRecipe(this.key);
    }
}