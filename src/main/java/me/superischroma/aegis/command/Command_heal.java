package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import org.bukkit.entity.Player;

@CommandParameters(description = "Set someone or yourself to full health.", usage = "/<command> [player]", aliases = "aheal")
public class Command_heal extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length > 1);
        if (args.length == 0 && !sender.isPlayer())
            throw new CommandFailException("onlyInGame");
        Player player = sender.getPlayer();
        if (args.length == 1)
            player = getNonNullPlayer(args[0]);
        if (plugin.puh.hasHealth(player))
            plugin.puh.setHealth(player, User.getUser(player).getMaxHealth());
        sendf(args.length == 0 ? "healedYourself" : "healed", player.getName());
    }
}