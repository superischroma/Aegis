package me.superischroma.aegis.command;

import me.superischroma.aegis.util.AUtil;

public class CommandFailException extends RuntimeException
{
    public CommandFailException(String message, Object... objects)
    {
        super(AUtil.f(message, objects));
    }
}