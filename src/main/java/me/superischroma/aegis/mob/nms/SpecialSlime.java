package me.superischroma.aegis.mob.nms;

import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpecialSlime extends EntitySlime implements NMSEntity
{
    private boolean scaleUp;
    private int size;

    public SpecialSlime()
    {
        super(EntityTypes.SLIME, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
        this.scaleUp = true;
        this.size = 5;
        // this will make the slime attacks anything
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget<>(this, EntityInsentient.class, false, false));
        this.setSize(5, true);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (scaleUp)
        {
            if (size == 30)
                scaleUp = false;
            size++;
        }
        else
        {
            if (size == 2)
                scaleUp = true;
            size--;
        }
        this.setSize(size, true);
    }

    @Override
    public EnumInteractionResult b(EntityHuman human, EnumHand hand)
    {
        human.startRiding(this);
        return EnumInteractionResult.PASS;
    }

    @Override
    public void spawn(Location location)
    {
        this.setPosition(location.getX(), location.getY(), location.getZ());
        ((CraftWorld) location.getWorld()).getHandle().addEntity(this, CreatureSpawnEvent.SpawnReason.CUSTOM);
    }
}