package me.superischroma.aegis.item;

public enum AegisItemType
{
    ADMIN_MENU(AdminMenu.class),
    BUTTERFLY_BOOTS(ButterflyBoots.class),
    CONDENSED_FUSE_SOUL(CondensedFuseSoul.class),
    CRUSHER(Crusher.class),
    ENCHANTER(Enchanter.class),
    FUSE_BOW(FuseBow.class),
    FUSE_SOUL(FuseSoul.class),
    HUB_BLOCK(HubBlock.class),
    HUB_MENU(HubMenu.class),
    KABOOM(Kaboom.class),
    MAGICAL_SADDLE(MagicalSaddle.class),
    MERGER(Merger.class),
    NUKE(Nuke.class),
    REINFORCER(Reinforcer.class),
    REINFORCER_STONE(ReinforcerStone.class),
    STRONG_FEATHER(StrongFeather.class),
    GODS_ESSENCE(GodsEssence.class),
    GODS_HELMET(GodsHelmet.class),
    GODS_CHESTPLATE(GodsChestplate.class),
    GODS_LEGGINGS(GodsLeggings.class),
    GODS_BOOTS(GodsBoots.class),
    TEST(Test.class),
    TNT_DISPENSER(TNTDispenser.class),
    TRASH_CAN(TrashCan.class),
    VERY_DEFENSIVE_CHESTPLATE(VeryDefensiveChestplate.class),
    VERY_POWERFUL_SWORD(VeryPowerfulSword.class),
    WATER_BALLOON(WaterBalloon.class);

    private final Class<? extends AegisItem> clazz;

    AegisItemType(Class<? extends AegisItem> clazz)
    {
        this.clazz = clazz;
    }

    public Class<? extends AegisItem> getItemClass()
    {
        return clazz;
    }

    public AegisItem newInstance()
    {
        AegisItem item;
        try
        {
            item = getItemClass().newInstance();
        }
        catch (IllegalAccessException | InstantiationException ex)
        {
            return null;
        }
        return item;
    }

    public static AegisItemType findType(String s)
    {
        return valueOf(s.toUpperCase());
    }
}