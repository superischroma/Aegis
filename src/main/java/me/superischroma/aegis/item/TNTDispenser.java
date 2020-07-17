package me.superischroma.aegis.item;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.block.data.Directional;
import org.bukkit.util.Vector;

public class TNTDispenser extends AegisItem
{
    public TNTDispenser()
    {
        super("TNT Dispenser", Material.DISPENSER, Rarity.SPECIAL);
        super.glow();
        super.applyMetaToStack();
    }

    @Override
    public void onBlockInteract(PlayerInteractEvent e)
    {
        e.setCancelled(true);
        BlockState block = e.getClickedBlock().getState();
        if (!(block instanceof Dispenser))
            return;
        Dispenser dispenser = (Dispenser) block;
        if (!(dispenser.getBlockData() instanceof Directional))
            return;
        Directional directional = (Directional) dispenser.getBlockData();
        TNTPrimed tnt = dispenser.getWorld().spawn(dispenser.getLocation().add(0.5, 0, 0.5), TNTPrimed.class);
        tnt.setFuseTicks(20);
        tnt.setYield(5.0f);
        tnt.setVelocity(tnt.getVelocity().clone().add(new Vector(
                directional.getFacing().getModX() * 3,
                directional.getFacing().getModY() * 3,
                directional.getFacing().getModZ() * 3
        )));
    }
}
