package me.superischroma.aegis.util;

import me.superischroma.aegis.config.AegisVariable;

public class ATranslator
{
    public Object tl(String t)
    {
        for (AegisVariable var : AegisVariable.values())
        {
            if (t.equals(var.getName()))
            {
                return var.getContent();
            }
        }
        return null;
    }

    public Object ttl(String t)
    {
        for (AegisVariable variable : AegisVariable.values())
        {
            if (t.contains(variable.getName()))
            {
                t = t.replaceAll(variable.getName(), variable.getContent().toString());
            }
        }
        return AUtil.colorize(t);
    }
}