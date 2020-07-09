package me.superischroma.aegis.mob;

public enum AegisMobType
{
    FUSE(Fuse.class),
    SLENDERMAN(Slenderman.class),
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

    public static AegisMobType findType(String s)
    {
        return valueOf(s.toUpperCase());
    }
}