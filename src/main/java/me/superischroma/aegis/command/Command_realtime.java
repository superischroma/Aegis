package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.config.ConfigEntry;
import me.superischroma.aegis.rank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

@CommandParameters(description = "Toggle real time mode.", usage = "/<command> <on | off>", aliases = "rt", root = true, rank = Rank.SUPER)
public class Command_realtime extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 1);
        switch (args[0].toLowerCase())
        {
            case "on":
            {
                World world = Bukkit.getWorld(ConfigEntry.REAL_TIME_WORLD.getString());
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
                ConfigEntry.REAL_TIME_ENABLED.set(true);
                sendf("realTimeEnabled");
                return;
            }
            case "off":
            {
                World world = Bukkit.getWorld(ConfigEntry.REAL_TIME_WORLD.getString());
                world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true);
                world.setTime(0);
                ConfigEntry.REAL_TIME_ENABLED.set(false);
                sendf("realTimeDisabled");
                return;
            }
        }
        throw new CommandArgumentException();
    }
}