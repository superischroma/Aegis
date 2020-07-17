package me.superischroma.aegis.mob;

import me.superischroma.aegis.item.FuseSoul;
import org.bukkit.ChatColor;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Fuse extends AegisMob
{
    public Fuse()
    {
        super("Fuse", EntityType.CREEPER, 100.0, 25);
        super.addDrop(new FuseSoul(), 100.0);
        super.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999999, 1));
    }
}