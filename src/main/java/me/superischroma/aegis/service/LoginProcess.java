package me.superischroma.aegis.service;

import me.superischroma.aegis.config.ConfigEntry;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginProcess extends AegisService
{
    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e)
    {
        Player player = e.getPlayer();
        if (ConfigEntry.WHITELIST_ENABLED.getBoolean() &&
                !ConfigEntry.WHITELIST_LIST.getStringList().contains(player.getName()) &&
                !plugin.rm.getRank(player).isAtLeast(Rank.BRUH))
        {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, AUtil.colorize(ConfigEntry.WHITELIST_MESSAGE.getString()));
        }
    }
}