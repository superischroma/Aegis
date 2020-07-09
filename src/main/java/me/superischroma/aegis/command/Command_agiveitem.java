package me.superischroma.aegis.command;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.item.AegisItemType;
import org.bukkit.ChatColor;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

@CommandParameters(description = "Give an item from Aegis to yourself.", usage = "/<command> <item>", source = CommandSource.IN_GAME)
public class Command_agiveitem extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args) throws Exception
    {
        checkArgs(args.length != 1);
        AegisItemType type = AegisItemType.findType(args[0]);
        if (type == null)
            throw new Exception("Invalid type.");
        PlayerInventory inv = sender.getPlayer().getInventory();
        if (inv.firstEmpty() > inv.getSize() - 1)
            throw new Exception("No space in inventory.");
        AegisItem item = type.getItemClass().newInstance();
        inv.setItem(inv.firstEmpty(), item.getStack());
        send("Gave a(n) " + item.getName() + ChatColor.GRAY + ".");
    }

    @Override
    public List<String> getTabCompleteOptions(CommandUser user, String[] args)
    {
        if (args.length == 1)
        {
            List<String> list = new ArrayList<>();
            for (AegisItemType type : AegisItemType.values())
            {
                list.add(type.name());
            }
            return list;
        }
        return null;
    }
}