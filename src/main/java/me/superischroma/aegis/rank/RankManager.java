package me.superischroma.aegis.rank;

import me.superischroma.aegis.User;
import me.superischroma.aegis.command.CommandUser;
import me.superischroma.aegis.service.AegisService;
import org.bukkit.entity.Player;

public class RankManager extends AegisService
{
    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
    }

    public Rank getRank(User user)
    {
        return user.getRank();
    }

    public Rank getRank(Player player)
    {
        return User.getUser(player).getRank();
    }

    public Rank getRank(CommandUser sender)
    {
        return sender.getUser().getRank();
    }

    public boolean isElevated(User user)
    {
        return getRank(user).isElevated();
    }

    public boolean isElevated(Player player)
    {
        return getRank(player).isElevated();
    }

    public boolean isElevated(CommandUser sender)
    {
        return getRank(sender).isElevated();
    }
}