package me.superischroma.aegis.item;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class Nuke extends SkullBased
{
    private static List<ArmorStand> THROWN_NUKES = new ArrayList<>();
    public static List<ArmorStand> getThrownNukes()
    {
        return THROWN_NUKES;
    }

    public Nuke()
    {
        super("Nuke", Rarity.SPECIAL);
        super.setSkullURL("e0c324acaa6949651dd2b0afe6e81de87e52a0dc96532b231eb27d05d725e");
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
        stand.getEquipment().setHelmet(new Nuke().getStack());
        stand.setVelocity(player.getLocation().getDirection().multiply(1.5));
        THROWN_NUKES.add(stand);
    }
}