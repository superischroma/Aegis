package me.superischroma.aegis.command;

import me.superischroma.aegis.User;
import me.superischroma.aegis.rank.Rank;

@CommandParameters(description = "Kills all Aegis mobs.", rank = Rank.BRUH)
public class Command_akillmobs extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 0);
        plugin.amm.killMobs();
        send("Killed all Aegis mobs.");
    }
}