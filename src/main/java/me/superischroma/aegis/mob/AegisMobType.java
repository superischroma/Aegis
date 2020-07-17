package me.superischroma.aegis.mob;

public enum AegisMobType
{
    FUSE(Fuse.class),
    ZEALOT(Zealot.class),
    TARANTULA(Tarantula.class),
    TEST(Test.class);

    private final Class<? extends AegisMob> clazz;

    AegisMobType(Class<? extends AegisMob> clazz)
    {
        this.clazz = clazz;
    }

    public Class<? extends AegisMob> getMobClass()
    {
        return clazz;
    }

    public AegisMob newInstance()
    {
        AegisMob mob;
        try
        {
            mob = clazz.newInstance();
        }
        catch (IllegalAccessException | InstantiationException ex)
        {
            return null;
        }
        return mob;
    }

    public static AegisMobType findType(String s)
    {
        return valueOf(s.toUpperCase());
    }
}