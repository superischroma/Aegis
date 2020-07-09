package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.mob.AegisMob;
import me.superischroma.aegis.mob.AegisMobType;

import java.util.ArrayList;
import java.util.List;

@CommandParameters(description = "Spawn a mob from Aegis.", usage = "/<command> <mob>", source = CommandSource.IN_GAME)
public class Command_aspawnmob extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args) throws Exception
    {
        checkArgs(args.length != 1);
        AegisMobType type = AegisMobType.findType(args[0]);
        if (type == null)
            throw new Exception("Invalid type.");
        AegisMob mob = type.getMobClass().newInstance();
        mob.spawn(sender.getPlayer().getLocation());
        send("Spawned a(n) " + mob.getName() + ".");
    }

    @Override
    public List<String> getTabCompleteOptions(CommandUser user, String[] args)
    {
        if (args.length == 1)
        {
            List<String> list = new ArrayList<>();
            for (AegisMobType type : AegisMobType.values())
            {
                list.add(type.name());
            }
            return list;
        }
        return null;
    }
}