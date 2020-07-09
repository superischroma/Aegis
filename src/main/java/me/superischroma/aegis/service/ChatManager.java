package me.superischroma.aegis.service;

import me.superischroma.aegis.user.User;
import me.superischroma.aegis.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatManager extends AegisService
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
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        User user = User.getUser(e.getPlayer());
        Rank rank = user.getRank();
        String format = user.getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + e.getMessage();
        if (rank != Rank.DEFAULT)
        {
            format = ChatColor.DARK_GRAY + "[" + rank.getColor() + rank.getName() + ChatColor.DARK_GRAY + "] " + rank.getColor() + format;
        }
        e.setFormat(format);
    }
}