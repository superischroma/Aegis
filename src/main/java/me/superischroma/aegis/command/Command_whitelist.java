package me.superischroma.aegis.command;

import me.superischroma.aegis.config.ConfigEntry;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.user.User;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.ArrayList;

@CommandParameters(description = "Manage the whitelist.", usage = "/<command> [on | off | add <name> | remove <name> | clear]", aliases = "awhitelist", rank = Rank.BRUH)
public class Command_whitelist extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length > 2);
        if (args.length == 2)
        {
            String name = args[1];
            switch (args[0].toLowerCase())
            {
                case "add":
                {
                    if (plugin.rm.getRank(name).isAtLeast(Rank.BRUH))
                        throw new CommandFailException("whitelistNoRanked", name);
                    boolean add = ConfigEntry.WHITELIST_LIST.addToStringList(name);
                    sendf(add ? "whitelistAdded" : "whitelistAlreadyAdded", name);
                    return;
                }
                case "remove":
                {
                    if (plugin.rm.getRank(name).isAtLeast(Rank.BRUH))
                        throw new CommandFailException("whitelistNoRanked", name);
                    boolean remove = ConfigEntry.WHITELIST_LIST.removeFromStringList(name);
                    sendf(remove ? "whitelistRemoved" : "whitelistNotAdded", name);
                    return;
                }
            }
            throw new CommandArgumentException();
        }
        if (args.length == 1)
        {
            switch (args[0].toLowerCase())
            {
                case "on":
                {
                    ConfigEntry.WHITELIST_ENABLED.set(true);
                    sendf("whitelistEnabled");
                    return;
                }
                case "off":
                {
                    ConfigEntry.WHITELIST_ENABLED.set(false);
                    sendf("whitelistDisabled");
                    return;
                }
                case "clear":
                {
                    ConfigEntry.WHITELIST_LIST.set(new ArrayList<>());
                    sendf("whitelistCleared");
                    return;
                }
            }
            throw new CommandArgumentException();
        }
        StringBuilder sb = new StringBuilder()
                .append("State: " + (ConfigEntry.WHITELIST_ENABLED.getBoolean() ? "Enabled" : "Disabled") + "\n")
                .append("List: ")
                .append("\n - Ranked Users");
        for (String name : ConfigEntry.WHITELIST_LIST.getStringList())
        {
            sb.append("\n - " + name);
        }
        send(sb.toString());
    }
}