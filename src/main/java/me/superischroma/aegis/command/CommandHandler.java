package me.superischroma.aegis.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler
{
    private List<AegisCommand> commands;

    public CommandHandler()
    {
        commands = new ArrayList<>();
    }

    public void add(AegisCommand command)
    {
        commands.add(command);
        command.register();
    }

    public int getCommandAmount()
    {
        return commands.size();
    }
}