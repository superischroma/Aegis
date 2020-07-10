package me.superischroma.aegis.gui;

public enum GUIType
{
    ADMIN(AdminGUI.class),
    CRUSHER(CrusherGUI.class),
    ENCHANTER(EnchanterGUI.class),
    HUB(HubGUI.class),
    MERGER(MergerGUI.class),
    REINFORCER(ReinforcerGUI.class);

    private final Class<? extends GUI> clazz;

    GUIType(Class<? extends GUI> clazz)
    {
        this.clazz = clazz;
    }

    public Class<? extends GUI> getGUIClass()
    {
        return clazz;
    }

    public static GUIType findType(String s)
    {
        return valueOf(s.toUpperCase());
    }
}