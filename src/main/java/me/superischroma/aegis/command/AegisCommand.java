package me.superischroma.aegis.command;

import me.superischroma.aegis.Aegis;
import me.superischroma.aegis.user.User;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.util.AUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AegisCommand implements CommandExecutor, TabCompleter
{
    public static final String COMMAND_PREFIX = "Command_";

    private static CommandMap cmap;
    private final String name;
    private final String description;
    private final String usage;
    private final String aliases;
    private final CommandSource source;
    private final CommandParameters params;

    private CommandUser sender;
    private String[] args;

    protected final Aegis plugin = Aegis.getPlugin();
    protected final Server server = plugin.getServer();

    protected final String NO_PERMISSION = "No permission.";
    protected final String ONLY_IN_GAME = "Console senders are not allowed to execute this command!";
    protected final String ONLY_CONSOLE = "Only console senders are allowed to execute this command!";
    protected final String PLAYER_NOT_FOUND = "Player not found.";
    protected final String CONFIG_ERROR = "There is an issue with a configuration entry. Please contact the server's administrator.";

    AegisCommand()
    {
        params = getClass().getAnnotation(CommandParameters.class);
        this.name = getClass().getSimpleName().replace(COMMAND_PREFIX, "").toLowerCase();
        this.description = params.description();
        this.usage = params.usage();
        this.aliases = params.aliases();
        this.source = params.source();
    }

    public void register()
    {
        ACommand cmd = new ACommand(this.name);
        if (this.aliases != null) cmd.setAliases(Arrays.asList(StringUtils.split(this.aliases, ",")));
        if (this.description != null) cmd.setDescription(this.description);
        if (this.usage != null) cmd.setUsage(this.usage);
        getCommandMap().register("", cmd);
        cmd.setExecutor(this);
    }

    final CommandMap getCommandMap()
    {
        if (cmap == null)
        {
            try
            {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                cmap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else if (cmap != null)
        {
            return cmap;
        }
        return getCommandMap();
    }

    private final class ACommand extends Command
    {
        private AegisCommand cmd = null;
        private ACommand(String command)
        {
            super(command);
        }
        public void setExecutor(AegisCommand cmd)
        {
            this.cmd = cmd;
        }

        public boolean execute(CommandSender sender, String c, String[] args)
        {
            if (cmd != null)
            {
                cmd.sender = new CommandUser(sender);
                cmd.args = args;

                if (!plugin.rm.getRank(cmd.sender).isAtLeast(params.rank()))
                {
                    sendf("rankIsNotAtLeast", ChatColor.RED, params.rank().getName());
                    return true;
                }

                if (params.root() && !cmd.sender.getUser().isRoot())
                {
                    sendf("noPermission", ChatColor.RED);
                    return true;
                }

                if (params.source() == CommandSource.IN_GAME && sender instanceof ConsoleCommandSender)
                {
                    sendf("onlyInGame", ChatColor.RED);
                    return true;
                }

                if (params.source() == CommandSource.CONSOLE && sender instanceof Player)
                {
                    sendf("onlyConsole", ChatColor.RED);
                    return true;
                }
                return cmd.onCommand(sender, this, c, args);
            }
            return false;
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String alias, String[] args)
        {
            if (cmd != null)
            {
                return cmd.onTabComplete(sender, this, alias, args);
            }
            return null;
        }
    }

    protected void send(String s, CommandUser sender)
    {
        sender.getBase().sendMessage(AUtil.getBaseColor() + s);
    }

    protected void send(String s, CommandSender sender)
    {
        sender.sendMessage(AUtil.getBaseColor() + s);
    }

    protected void send(String s, Player player)
    {
        player.sendMessage(AUtil.getBaseColor() + s);
    }

    protected void send(String s, User user)
    {
        user.sendMessage(AUtil.getBaseColor() + s);
    }

    protected void send(String s, ChatColor color)
    {
        send(color + s);
    }

    protected void send(String s)
    {
        send(s, sender);
    }

    protected void sendf(String s, Object... objects)
    {
        send(AUtil.f(s, objects));
    }

    protected void sendf(String s, CommandUser sender, Object... objects)
    {
        send(AUtil.f(s, objects), sender);
    }

    protected void sendf(String s, CommandSender sender, Object... objects)
    {
        send(AUtil.f(s, objects), sender);
    }

    protected void sendf(String s, Player player, Object... objects)
    {
        send(AUtil.f(s, objects), player);
    }

    protected void sendf(String s, User user, Object... objects)
    {
        send(AUtil.f(s, objects), user);
    }

    protected void sendf(String s, ChatColor color, Object... objects)
    {
        send(AUtil.f(s, objects), color);
    }

    protected void blast(String s)
    {
        Bukkit.broadcastMessage(s);
    }

    protected User getUser(String name)
    {
        Player player = getPlayer(name);
        if (player == null)
            return User.getUser(name);
        return User.getUser(player);
    }

    protected void checkArgs(boolean failCondition)
    {
        if (failCondition)
        {
            throw new CommandArgumentException();
        }
    }

    protected void checkRank(Rank rank)
    {
        if (!plugin.rm.getRank(sender).isAtLeast(rank))
        {
            throw new CommandFailException("rankIsNotAtLeast", rank.getName());
        }
    }

    protected void argsFailure()
    {
        throw new CommandArgumentException();
    }

    protected void onlyConsoleFailure()
    {
        throw new CommandFailException(ONLY_CONSOLE);
    }

    protected void onlyInGameFailure()
    {
        throw new CommandFailException(ONLY_IN_GAME);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args)
    {
        CommandUser cu = new CommandUser(sender);
        try
        {
            run(cu, cu.getUser(), args);
        }
        catch (CommandArgumentException ex)
        {
            send(cmd.getUsage().replace("<command>", cmd.getLabel()), ChatColor.WHITE);
            return true;
        }
        catch (PlayerNotFoundException ex)
        {
            send(AUtil.getErrorColor() + AUtil.f("playerNotFound"));
        }
        catch (CommandFailException ex)
        {
            ex.printStackTrace();
            send(AUtil.getErrorColor() + ex.getMessage());
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command cmd, String c, String[] args)
    {
        List<String> options = getTabCompleteOptions(this.sender, args);
        if (options == null)
        {
            return AUtil.getPlayerList();
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], options, new ArrayList<>());
    }

    public abstract void run(CommandUser sender, User user, String[] args);

    protected List<String> getTabCompleteOptions(CommandUser sender, String[] args)
    {
        return AUtil.getPlayerList();
    }

    protected boolean isConsole()
    {
        return sender.getBase() instanceof ConsoleCommandSender;
    }


    protected Player getPlayer(String name)
    {
        return Bukkit.getPlayer(name);
    }

    protected int parseInt(String s)
    {
        int i;
        try
        {
            i = Integer.parseInt(s);
        }
        catch (NumberFormatException ex)
        {
            throw new CommandFailException("invalidNumber");
        }
        return i;
    }

    protected Player getNonNullPlayer(String name)
    {
        Player player = getPlayer(name);
        if (player == null)
            throw new PlayerNotFoundException();
        return player;
    }
}