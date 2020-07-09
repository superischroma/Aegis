package me.superischroma.aegis.command;

public class CommandFailException extends RuntimeException
{
    public CommandFailException(String message)
    {
        super(message);
    }
}