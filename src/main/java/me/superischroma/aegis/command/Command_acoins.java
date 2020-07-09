package me.superischroma.aegis.command;

import me.superischroma.aegis.User;
import me.superischroma.aegis.mob.AegisMob;
import me.superischroma.aegis.mob.AegisMobType;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.entity.Player;

import javax.swing.undo.AbstractUndoableEdit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandParameters(description = "Modify coins.", usage = "/<command> <add | sub | set> <player> <value>", source = CommandSource.IN_GAME)
public class Command_acoins extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args) throws Exception
    {
        switch (args[0].toLowerCase())
        {
            case "add":
            {
                User player = getUser(args[1]);
                long additive;
                try { additive = Long.parseLong(args[2]); } catch (NumberFormatException ex) { throw new Exception("Invalid number."); }
                player.setCoins(player.getCoins() + additive);
                player.save();
                send("You have added " + AUtil.getCommaSpacedLong(additive) + " coin(s) to " + player.getName() + "'s current amount.");
                return;
            }
            case "sub":
            {
                User player = getUser(args[1]);
                long s;
                try { s = Long.parseLong(args[2]); } catch (NumberFormatException ex) { throw new Exception("Invalid number."); }
                player.setCoins(player.getCoins() - s);
                player.save();
                send("You have subtracted " + AUtil.getCommaSpacedLong(s) + " coin(s) from " + player.getName() + "'s current amount.");
                return;
            }
            case "set":
            {
                User player = getUser(args[1]);
                long set;
                try { set = Long.parseLong(args[2]); } catch (NumberFormatException ex) { throw new Exception("Invalid number."); }
                player.setCoins(set);
                player.save();
                send("You have set " + player.getName() + "'s amount to " + AUtil.getCommaSpacedLong(set) + " coin(s).");
                return;
            }
        }
        throw new CommandArgumentException();
    }

    @Override
    public List<String> getTabCompleteOptions(CommandUser user, String[] args)
    {
        if (args.length == 1)
        {
            return Arrays.asList("add", "sub", "set");
        }
        if (args.length == 2)
        {
            return AUtil.getPlayerList();
        }
        if (args.length == 3)
        {
            return Collections.singletonList("<value>");
        }
        return null;
    }
}