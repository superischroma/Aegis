package me.superischroma.aegis.mob.nms;

import net.minecraft.server.v1_16_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class RideableWither extends EntityWither implements NMSEntity
{
    public RideableWither()
    {
        super(EntityTypes.WITHER, ((CraftWorld) Bukkit.getWorld("world")).getHandle());
    }

    @Override
    public void tick()
    {
        super.tick();
        if (passengers.size() < 1)
            return;
        Entity controller = passengers.get(0);
        this.yaw = controller.yaw;
        this.setYawPitch(this.yaw, this.pitch);
        this.aJ = this.yaw;
    }

    @Override
    protected void initPathfinder()
    {
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