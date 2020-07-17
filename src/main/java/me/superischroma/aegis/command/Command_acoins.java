package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.util.AUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandParameters(description = "Modify coins.", usage = "/<command> <add | sub | set> <player> <value>", source = CommandSource.IN_GAME)
public class Command_acoins extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        switch (args[0].toLowerCase())
        {
            case "add":
            {
                User player = getUser(args[1]);
                long additive;
                try { additive = Long.parseLong(args[2]); } catch (NumberFormatException ex) { throw new CommandFailException("invalidNumber"); }
                player.setCoins(player.getCoins() + additive);
                player.save();
                sendf("addedCoins", AUtil.getCommaSpacedLong(additive), player.getName());
                return;
            }
            case "sub":
            {
                User player = getUser(args[1]);
                long s;
                try { s = Long.parseLong(args[2]); } catch (NumberFormatException ex) { throw new CommandFailException("invalidNumber"); }
                player.setCoins(player.getCoins() - s);
                player.save();
                sendf("subtractedCoins", AUtil.getCommaSpacedLong(s), player.getName());
                return;
            }
            case "set":
            {
                User player = getUser(args[1]);
                long set;
                try { set = Long.parseLong(args[2]); } catch (NumberFormatException ex) { throw new CommandFailException("invalidNumber"); }
                player.setCoins(set);
                player.save();
                sendf("setCoins", AUtil.getCommaSpacedLong(set), player.getName());
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