package me.superischroma.aegis.mob;

import lombok.Getter;
import lombok.Setter;
import me.superischroma.aegis.Aegis;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;

public class AegisSpawnedMob
{
    private static Aegis plugin = Aegis.getPlugin();

    @Getter
    private AegisMob base;

    @Getter
    private String mobName;

    @Getter
    private String name;

    @Getter
    private Entity entity;

    private ArmorStand tag;

    @Getter
    @Setter
    private double health;

    @Getter
    private double maxHealth;

    public AegisSpawnedMob(AegisMob base, Entity entity)
    {
        this.base = base;
        this.mobName = base.getName();
        this.name = ChatColor.GREEN + base.getName() + " " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + (int) (base.getMaxHealth()) + "/" + (int) (base.getMaxHealth()) + " HP" + ChatColor.DARK_GRAY + "]";
        this.entity = entity;
        getLivingEntity().setRemoveWhenFarAway(false);
        for (PotionEffect effect : base.getPotionEffects())
        {
            getLivingEntity().addPotionEffect(effect);
        }
        getDamageableEntity().setMaxHealth(base.getMaxHealth());
        getDamageableEntity().setHealth(base.getMaxHealth());
        this.tag = (ArmorStand) entity.getLocation().getWorld().spawnEntity(entity.getLocation().clone().add(0, getTagHeight(entity.getType()), 0), EntityType.ARMOR_STAND);
        this.health = base.getMaxHealth();
        this.maxHealth = base.getMaxHealth();
        this.tag.setGravity(false);
        this.tag.setVisible(false);
        this.tag.setInvulnerable(true);
        this.tag.setAI(false);
        this.tag.setCustomName(this.name);
        this.tag.setCustomNameVisible(true);
        plugin.amm.addMob(this);
    }

    public void kill()
    {
        setHealth(0.0);
        tag.remove();
    }

    public void refreshTag()
    {
        tag.teleport(entity.getLocation().clone().add(0, getTagHeight(entity.getType()), 0));
        name = ChatColor.GREEN + mobName + " " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + (int) (health) + "/" + (int) (maxHealth) + " HP" + ChatColor.DARK_GRAY + "]";
        tag.setCustomName(name);
    }

    private Damageable getDamageableEntity()
    {
        return (Damageable) entity;
    }

    private LivingEntity getLivingEntity()
    {
        return (LivingEntity) entity;
    }

    private static double getTagHeight(EntityType type)
    {
        switch (type)
        {
            case ENDERMAN: return 1.0;
            case SPIDER: return -1.0;
            default: return 0.0;
        }
    }
}