package me.superischroma.aegis.config;

import me.superischroma.aegis.Aegis;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config extends YamlConfiguration
{
    private final Aegis plugin;
    private final File file;

    public Config(String name)
    {
        this.plugin = Aegis.getPlugin();
        this.file = new File(plugin.getDataFolder(), name);

        if (!file.exists())
        {
            options().copyDefaults(true);
            plugin.saveResource(name, false);
        }
        load();
    }

    public void load()
    {
        try
        {
            super.load(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void save()
    {
        try
        {
            super.save(file);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}