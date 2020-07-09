package me.superischroma.aegis.config;

import me.superischroma.aegis.Aegis;

import java.util.List;

public enum ConfigEntry
{
    BOOT("boot"),
    SHUTDOWN("shutdown"),
    //
    REAL_TIME_ENABLED("real_time.enabled"),
    REAL_TIME_WORLD("real_time.world"),
    //
    MOTD("motd.message"),
    MOTD_RANDOMIZE("motd.randomize"),
    MOTD_LIST("motd.messages");

    private final String key;

    ConfigEntry(String key)
    {
        this.key = key;
    }

    private static Config config = Aegis.getPlugin().config;

    public Object get()
    {
        return config.get(key);
    }

    public String getString()
    {
        return config.getString(key);
    }

    public boolean getBoolean()
    {
        return config.getBoolean(key);
    }

    public int getInt()
    {
        return config.getInt(key);
    }

    public List<String> getStringList()
    {
        return config.getStringList(key);
    }

    public String tl()
    {
        return "" + Aegis.getPlugin().atl.ttl(config.getString(key));
    }

    public void set(Object value)
    {
        config.set(key, value);
        config.save();
    }
}