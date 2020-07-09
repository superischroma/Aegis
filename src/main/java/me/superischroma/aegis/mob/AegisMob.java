package me.superischroma.aegis.mob;

import lombok.Getter;
import me.superischroma.aegis.Aegis;
import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.util.RandomCollection;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class AegisMob
{
    private static Aegis plugin = Aegis.getPlugin();

    @Getter
    private String name;

    @Getter
    private EntityType type;

    @Getter
    private double maxHealth;

    @Getter
    private RandomCollection<ItemStack> drops;

    @Getter
    private List<PotionEffect> potionEffects;

    protected AegisMob(String name, EntityType type, double maxHealth)
    {
        this.name = name;
        this.type = type;
        this.maxHealth = maxHealth;
        this.drops = new RandomCollection<>();
        this.potionEffects = new ArrayList<>();
    }

    public void addDrop(ItemStack stack, double weight)
    {
        drops.add(weight, stack);
    }

    public void addDrop(AegisItem item, double weight)
    {
        addDrop(item.getStack(), weight);
    }

    public void addPotionEffect(PotionEffect effect)
    {
        potionEffects.add(effect);
    }

    public void spawn(Location location)
    {
        if (location.getWorld() == null)
            return;
        Entity spawned = location.getWorld().spawnEntity(location, type);
        new AegisSpawnedMob(this, spawned);
    }
}