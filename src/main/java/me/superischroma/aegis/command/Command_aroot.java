package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import org.bukkit.entity.Player;

@CommandParameters(description = "Modify the plugin root.", usage = "/<command> <add <player> | remove <player>>", root = true)
public class Command_aroot extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args) throws Exception
    {
        checkArgs(args.length != 2);
        User pl = getUser(args[1]);
        switch (args[0].toLowerCase())
        {
            case "add":
            {
                if (pl.isRoot())
                    throw new Exception("That user is already root!");
                pl.setRoot(true);
                pl.save();
                send(pl.getName() + " is now root.");
                send("You are now root.", pl);
                return;
            }
            case "remove":
            {
                if (!pl.isRoot())
                    throw new Exception("That user is not root!");
                pl.setRoot(false);
                pl.save();
                send(pl.getName() + " is no longer root.");
                Player player = getPlayer(args[1]);
                if (player != null)
                    send("You are no longer root.", player);
                return;
            }
        }
        throw new CommandArgumentException();
    }
}