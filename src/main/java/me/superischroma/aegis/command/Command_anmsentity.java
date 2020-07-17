package me.superischroma.aegis.command;

import me.superischroma.aegis.mob.nms.NMSEntity;
import me.superischroma.aegis.mob.nms.NMSEntityType;
import me.superischroma.aegis.user.User;

import java.util.ArrayList;
import java.util.List;

@CommandParameters(description = "Spawn an NMS entity from Aegis.", usage = "/<command> <entity>", source = CommandSource.IN_GAME)
public class Command_anmsentity extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 1);
        NMSEntityType type = NMSEntityType.findType(args[0]);
        if (type == null)
            throw new CommandFailException("invalidType");
        NMSEntity entity = type.newInstance();
        entity.spawn(sender.getPlayer().getLocation());
        sendf("entitySpawned", type.name());
    }

    @Override
    public List<String> getTabCompleteOptions(CommandUser user, String[] args)
    {
        if (args.length == 1)
        {
            List<String> list = new ArrayList<>();
            for (NMSEntityType type : NMSEntityType.values())
            {
                list.add(type.name());
            }
            return list;
        }
        return null;
    }
}