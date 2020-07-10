package me.superischroma.aegis.item.variant;

public enum VariantType
{
    TEST(Test.class);

    private final Class<? extends Variant> clazz;

    VariantType(Class<? extends Variant> clazz)
    {
        this.clazz = clazz;
    }

    public Class<? extends Variant> getVariantClass()
    {
        return clazz;
    }

    public Variant newInstance()
    {
        Variant item;
        try
        {
            item = getVariantClass().newInstance();
        }
        catch (IllegalAccessException | InstantiationException ex)
        {
            return null;
        }
        return item;
    }

    public static VariantType findType(String s)
    {
        return valueOf(s.toUpperCase());
    }
}