package me.superischroma.aegis.command;

import me.superischroma.aegis.User;
import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.AegisItemType;
import org.bukkit.ChatColor;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

@CommandParameters(description = "Reload the plugin's config files.", aliases = "asavec")
public class Command_asaveconfig extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 0);
        plugin.config.load();
        plugin.players.load();
        plugin.blocks.load();
        plugin.config.save();
        plugin.players.save();
        plugin.blocks.save();
        send("Saved current configuration states.");
    }
}