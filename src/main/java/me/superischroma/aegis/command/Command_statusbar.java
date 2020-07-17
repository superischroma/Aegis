package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
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
                sendf("statusBarEnabled");
                return;
            }
            case "off":
            {
                user.setStatusBar(false);
                user.save();
                sendf("statusBarDisabled");
                return;
            }
        }
        User player = getUser(args[0]);
        player.setStatusBar(!player.hasStatusBarEnabled());
        player.save();
        sendf(player.hasStatusBarEnabled() ? "statusBarEnabledOtherConfirm" : "statusBarDisabledOtherConfirm", player.getName());
        sendf(player.hasStatusBarEnabled() ? "statusBarEnabledOther" : "statusBarDisabledOther", player, user.getName());
    }
}