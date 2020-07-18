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
    MOTD_WHITELIST("motd.whitelist"),
    MOTD_RANDOMIZE("motd.randomize"),
    MOTD_LIST("motd.messages"),
    //
    WHITELIST_ENABLED("whitelist.enabled"),
    WHITELIST_LIST("whitelist.list"),
    WHITELIST_MESSAGE("whitelist.message");

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

    public boolean addToStringList(String element)
    {
        List<String> list = getStringList();
        boolean contains = !list.contains(element);
        if (contains)
            list.add(element);
        set(list);
        return contains;
    }

    public boolean removeFromStringList(String element)
    {
        List<String> list = getStringList();
        boolean remove = list.remove(element);
        set(list);
        return remove;
    }
}