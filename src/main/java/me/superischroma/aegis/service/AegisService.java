package me.superischroma.aegis.service;

import me.superischroma.aegis.Aegis;
import org.bukkit.event.Listener;

public abstract class AegisService implements Listener
{
    protected final Aegis plugin;

    public AegisService()
    {
        plugin = Aegis.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.ash.add(this);
    }

    public Object tl(String t)
    {
        return plugin.atl.tl(t);
    }

    public abstract void start();
    public abstract void stop();
}