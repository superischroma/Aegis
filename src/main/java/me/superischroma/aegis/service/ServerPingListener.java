package me.superischroma.aegis.service;

import me.superischroma.aegis.config.ConfigEntry;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.Random;

public class ServerPingListener extends AegisService
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
    public void onServerPing(ServerListPingEvent e)
    {
        if (ConfigEntry.WHITELIST_ENABLED.getBoolean() && ConfigEntry.MOTD_WHITELIST.getBoolean())
        {
            e.setMotd(AUtil.colorize(ConfigEntry.WHITELIST_MESSAGE.getString()));
            return;
        }
        if (ConfigEntry.MOTD_RANDOMIZE.getBoolean())
        {
            List<String> list = ConfigEntry.MOTD_LIST.getStringList();
            if (list.size() == 0)
                return;
            e.setMotd(AUtil.colorize("" + plugin.atl.ttl(list.get(new Random().nextInt(list.size() - 1)))));
            return;
        }
        String motd = ConfigEntry.MOTD.tl();
        if (motd.equals(""))
            return;
        e.setMotd(motd);
    }
}