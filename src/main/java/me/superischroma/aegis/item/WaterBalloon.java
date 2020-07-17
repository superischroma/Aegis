package me.superischroma.aegis.item;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class WaterBalloon extends SkullBased
{
    private static List<ArmorStand> THROWN_WATER_BALLOONS = new ArrayList<>();
    public static List<ArmorStand> getThrownWaterBalloons()
    {
        return THROWN_WATER_BALLOONS;
    }

    public WaterBalloon()
    {
        super("Water Balloon", Rarity.UNCOMMON);
        super.setSkullURL("61485349006ed1c91b7959aff44f330ddac35e3d9a99e4a82051f986ecda4755");
        super.applyMetaToStack();
    }

    @Override
    public void onItemInteract(PlayerInteractEvent e)
    {
        e.setCancelled(true);
        Player player = e.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            e.getItem().setAmount(e.getItem().getAmount() - 1);
        ArmorStand stand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);
        stand.setInvulnerable(true);
        stand.setVisible(false);
        stand.setSmall(true);
        stand.getEquipment().setHelmet(new WaterBalloon().getStack());
        stand.setVelocity(player.getLocation().getDirection().multiply(1.5));
        THROWN_WATER_BALLOONS.add(stand);
    }
}