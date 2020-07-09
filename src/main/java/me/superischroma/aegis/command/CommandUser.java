package me.superischroma.aegis.command;

import lombok.Getter;
import me.superischroma.aegis.User;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUser
{
    @Getter
    private CommandSender base;

    public CommandUser(CommandSender base)
    {
        this.base = base;
    }

    public boolean isPlayer()
    {
        return base instanceof Player;
    }

    public Player getPlayer()
    {
        return (Player) base;
    }

    public User getUser()
    {
        if (!isPlayer())
            return User.getUser(base.getName());
        return User.getUser(getPlayer());
    }

    public void sendMessage(String s)
    {
        base.sendMessage(s);
    }
}