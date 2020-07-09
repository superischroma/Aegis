package me.superischroma.aegis;

import me.superischroma.aegis.command.*;
import me.superischroma.aegis.config.Config;
import me.superischroma.aegis.config.ConfigEntry;
import me.superischroma.aegis.gui.GUIHandler;
import me.superischroma.aegis.item.AegisBlockHandler;
import me.superischroma.aegis.item.CustomCrafting;
import me.superischroma.aegis.leveling.LevelingListener;
import me.superischroma.aegis.mob.AegisMobManager;
import me.superischroma.aegis.rank.RankManager;
import me.superischroma.aegis.service.*;
import me.superischroma.aegis.user.PlayerUserHandler;
import me.superischroma.aegis.util.ALog;
import me.superischroma.aegis.util.ATranslator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Set;

public final class Aegis extends JavaPlugin
{
    private static Aegis plugin;
    private static PluginDescriptionFile info;
    public static Aegis getPlugin()
    {
        return plugin;
    }
    public static PluginDescriptionFile getInfo()
    {
        return info;
    }

    public Config config;
    public Config players;
    public Config blocks;

    public CommandHandler ch;
    public AegisServiceHandler ash;
    public TimeManager tm;
    public SidebarManager sbm;
    public ATranslator atl;
    public StatusBarManager stbm;
    public AegisMobManager amm;
    public RankManager rm;
    public ChatManager cm;
    public ServerPingListener spl;
    public CustomCrafting cc;
    public GUIHandler gh;
    public AegisBlockHandler abh;
    public FuseBowListener fbl;
    public ButterflyBootsListener bbl;
    public LevelingListener ll;
    public ActionBarManager abm;
    public PlayerUserHandler puh;

    @Override
    public void onEnable()
    {
        plugin = this;
        info = this.getDescription();
        config = new Config("config.yml");
        players = new Config("players.yml");
        blocks = new Config("blocks.yml");
        ch = new CommandHandler();
        ash = new AegisServiceHandler();
        atl = new ATranslator();
        loadCommands();
        loadServices();
        ALog.info(ConfigEntry.BOOT.tl());
    }

    @Override
    public void onDisable()
    {
        plugin = null;
        info = null;
        config.save();
        players.save();
        for (AegisService service : ash.getServices())
            service.stop();
        ALog.info(ConfigEntry.SHUTDOWN.tl());
    }

    private void loadCommands()
    {
        Reflections commandDir = new Reflections("me.superischroma.aegis.command");

        Set<Class<? extends AegisCommand>> commandClasses = commandDir.getSubTypesOf(AegisCommand.class);

        for (Class<? extends AegisCommand> commandClass : commandClasses)
        {
            try
            {
                ch.add(commandClass.newInstance());
            }
            catch (InstantiationException | IllegalAccessException | ExceptionInInitializerError ex)
            {
                ALog.warn("Failed to register command: /" + commandClass.getSimpleName().replace("Command_" , ""));
            }
        }
        ALog.info("Loaded " + ch.getCommandAmount() + " command(s).");
    }

    private void loadServices()
    {
        tm = new TimeManager();
        sbm = new SidebarManager();
        stbm = new StatusBarManager();
        amm = new AegisMobManager();
        rm = new RankManager();
        cm = new ChatManager();
        spl = new ServerPingListener();
        cc = new CustomCrafting();
        gh = new GUIHandler();
        abh = new AegisBlockHandler();
        fbl = new FuseBowListener();
        bbl = new ButterflyBootsListener();
        ll = new LevelingListener();
        abm = new ActionBarManager();
        puh = new PlayerUserHandler();
        ALog.info("Started " + ash.getServiceAmount() + " service(s).");
    }
}
