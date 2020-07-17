package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import org.bukkit.entity.Player;

@CommandParameters(description = "Modify the plugin root.", usage = "/<command> <add <player> | remove <player>>", root = true)
public class Command_aroot extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 2);
        User pl = getUser(args[1]);
        switch (args[0].toLowerCase())
        {
            case "add":
            {
                if (pl.isRoot())
                    throw new CommandFailException("alreadyRoot");
                pl.setRoot(true);
                pl.save();
                sendf("nowRootConfirm", pl.getName());
                sendf("nowRoot", pl);
                return;
            }
            case "remove":
            {
                if (!pl.isRoot())
                    throw new CommandFailException("notRoot");
                pl.setRoot(false);
                pl.save();
                sendf("noLongerRootConfirm", pl.getName());
                Player player = getPlayer(args[1]);
                if (player != null)
                    sendf("noLongerRoot", player);
                return;
            }
        }
        throw new CommandArgumentException();
    }
}