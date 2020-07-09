package me.superischroma.aegis.command;

import me.superischroma.aegis.User;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.util.AUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandParameters(description = "Ranking, ranking, and more ranking.", usage = "/<command> [set <player> <rank>]")
public class Command_rank extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args) throws Exception
    {
        checkArgs(args.length == 1 || args.length == 2 || args.length > 3);
        if (args.length == 0)
        {
            Rank rank = plugin.rm.getRank(sender);
            send("You are: " + rank.getColor() + rank.getName());
            return;
        }
        if (args[0].equalsIgnoreCase("set"))
        {
            checkRank(Rank.BRUH);
            User player = getUser(args[1]);
            Rank rank = Rank.findRank(args[2]);
            if (rank == null)
                throw new Exception("Invalid rank.");
            if (rank == Rank.GOD)
                throw new Exception("You cannot set someone to be God!");
            player.setRank(rank);
            player.save();
            send("Set " + player.getName() + "'s rank to " + rank.getName());
            send("Your rank is now " + rank.getName(), player);
        }
    }

    @Override
    public List<String> getTabCompleteOptions(CommandUser user, String[] args)
    {
        if (args.length == 1)
        {
            return Collections.singletonList("set");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set"))
        {
            return AUtil.getPlayerList();
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set"))
        {
            List<String> ranks = new ArrayList<>();
            for (Rank rank : Rank.values())
            {
                if (rank == Rank.GOD)
                    continue;
                ranks.add(rank.name());
            }
            return ranks;
        }
        return Collections.emptyList();
    }
}