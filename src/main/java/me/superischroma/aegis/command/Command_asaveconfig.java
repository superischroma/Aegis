package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;

@CommandParameters(description = "Reload the plugin's config files.", aliases = "asavec")
public class Command_asaveconfig extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 0);
        plugin.config.load();
        plugin.players.load();
        plugin.blocks.load();
        plugin.messages.load();
        plugin.config.save();
        plugin.players.save();
        plugin.blocks.save();
        plugin.messages.save();
        sendf("savedConfigurations");
    }
}