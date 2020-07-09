package me.superischroma.aegis.user;

import lombok.Getter;
import lombok.Setter;
import me.superischroma.aegis.Aegis;
import me.superischroma.aegis.item.AegisItem;
import me.superischroma.aegis.leveling.AegisLevel;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.util.ALog;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class User
{
    private static Aegis plugin = Aegis.getPlugin();
    private static final double BASE_HEALTH = 100.0;
    private static final int BASE_DEFENSE = 50;

    @Getter
    private String name;

    @Getter
    @Setter
    private boolean root;

    @Getter
    @Setter
    private Rank rank;

    @Setter
    private boolean statusBar;

    @Getter
    @Setter
    private long coins;

    private AegisLevel level;

    private User(String name, boolean root, Rank rank, boolean statusBar, long coins, AegisLevel level)
    {
        this.name = name;
        this.root = root;
        this.rank = rank;
        this.statusBar = statusBar;
        this.coins = coins;
        this.level = level;
    }

    public void save()
    {
        plugin.players.set(name.toLowerCase() + ".name", name);
        plugin.players.set(name.toLowerCase() + ".root", root);
        plugin.players.set(name.toLowerCase() + ".rank", rank.name());
        plugin.players.set(name.toLowerCase() + ".statusBar", statusBar);
        plugin.players.set(name.toLowerCase() + ".coins", coins);
        plugin.players.set(name.toLowerCase() + ".level", level.getLevel());
        plugin.players.set(name.toLowerCase() + ".progress", level.getProgress());
        plugin.players.set(name.toLowerCase() + ".nextLevel", level.getNextLevel());
        plugin.players.save();
    }

    public boolean isPlayer()
    {
        return Bukkit.getPlayer(name) != null;
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(name);
    }

    public void sendMessage(String message)
    {
        if (!isPlayer())
        {
            Bukkit.getConsoleSender().sendMessage(message);
            return;
        }
        getPlayer().sendMessage(message);
    }

    public void sendActionBar(String message)
    {
        if (!isPlayer())
            return;
        getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public boolean hasStatusBarEnabled()
    {
        return statusBar;
    }

    public double getLevelProgress()
    {
        return level.getProgress();
    }

    public void setLevelProgress(double progress)
    {
        level.setProgress(progress);
    }

    public int getLevel()
    {
        return level.getLevel();
    }

    public void setLevel(int level)
    {
        this.level.setLevel(level);
    }

    public int getNextLevel()
    {
        return level.getNextLevel();
    }

    public void setNextLevel(int nextLevel)
    {
        level.setNextLevel(nextLevel);
    }

    public void play(Sound sound)
    {
        if (!isPlayer())
            return;
        getPlayer().playSound(getPlayer().getLocation(), sound, 1F, 1F);
    }

    public double getMaxHealth()
    {
        if (!isPlayer())
            return 0.0;
        double finalHealth = BASE_HEALTH;
        PlayerInventory inv = getPlayer().getInventory();
        finalHealth += AegisItem.getHealthOffItem(inv.getHelmet());
        finalHealth += AegisItem.getHealthOffItem(inv.getChestplate());
        finalHealth += AegisItem.getHealthOffItem(inv.getLeggings());
        finalHealth += AegisItem.getHealthOffItem(inv.getBoots());
        return finalHealth;
    }

    public double getDefense()
    {
        if (!isPlayer())
            return 0.0;
        int finalHealth = BASE_DEFENSE;
        PlayerInventory inv = getPlayer().getInventory();
        finalHealth += AegisItem.getDefenseOffItem(inv.getHelmet());
        finalHealth += AegisItem.getDefenseOffItem(inv.getChestplate());
        finalHealth += AegisItem.getDefenseOffItem(inv.getLeggings());
        finalHealth += AegisItem.getDefenseOffItem(inv.getBoots());
        return finalHealth;
    }

    public static User getUser(Player player)
    {
        if (plugin.players.contains(player.getName().toLowerCase()))
        {
            return new User(player.getName(),
                    plugin.players.getBoolean(player.getName().toLowerCase() + ".root"),
                    Rank.findRank(plugin.players.getString(player.getName().toLowerCase() + ".rank")),
                    plugin.players.getBoolean(player.getName().toLowerCase() + ".statusBar"),
                    plugin.players.getLong(player.getName().toLowerCase() + ".coins"),
                    new AegisLevel(plugin.players.getInt(player.getName().toLowerCase() + ".level"),
                            plugin.players.getDouble(player.getName().toLowerCase() + ".progress"),
                            plugin.players.getInt(player.getName().toLowerCase() + ".nextLevel")));
        }
        ALog.info("Creating new player entry for " + player.getName());
        User user = new User(player.getName(), false, Rank.DEFAULT,false, 0L, new AegisLevel(1, 0.0, 100));
        user.save();
        return user;
    }

    public static User getUser(String name)
    {
        if (name.equals("CONSOLE"))
            return new User("CONSOLE", true, Rank.GOD, false, Long.MAX_VALUE, new AegisLevel(50, 0.0, 0));
        if (!plugin.players.contains(name.toLowerCase()))
            return null;
        return new User(name,
                plugin.players.getBoolean(name.toLowerCase() + ".root"),
                Rank.findRank(plugin.players.getString(name.toLowerCase() + ".rank")),
                plugin.players.getBoolean(name.toLowerCase() + ".statusBar"),
                plugin.players.getLong(name.toLowerCase() + ".coins"),
                new AegisLevel(plugin.players.getInt(name.toLowerCase() + ".level"),
                        plugin.players.getDouble(name.toLowerCase() + ".progress"),
                        plugin.players.getInt(name.toLowerCase() + ".nextLevel")));
    }

    public static List<User> getOnlineUsers()
    {
        List<User> users = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            users.add(getUser(player));
        }
        return users;
    }
}