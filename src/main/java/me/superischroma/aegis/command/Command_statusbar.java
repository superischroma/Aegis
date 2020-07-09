package me.superischroma.aegis.command;

import me.superischroma.aegis.User;
import me.superischroma.aegis.rank.Rank;

@CommandParameters(description = "Toggle your status bar.", usage = "/<command> <on | off | player>", aliases = "sb", source = CommandSource.IN_GAME, rank = Rank.SUPER)
public class Command_statusbar extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 1);
        switch (args[0].toLowerCase())
        {
            case "on":
            {
                user.setStatusBar(true);
                user.save();
                send("Enabled status bar.");
                return;
            }
            case "off":
            {
                user.setStatusBar(false);
                user.save();
                send("Disabled status bar.");
                return;
            }
        }
        User player = getUser(args[0]);
        player.setStatusBar(!player.hasStatusBarEnabled());
        player.save();
        send((player.hasStatusBarEnabled() ? "Enabled" : "Disabled") + " " + player.getName() + "'s status bar.");
        send(user.getName() + " has " + (player.hasStatusBarEnabled() ? "enabled" : "disabled") + " your status bar.");
    }
}