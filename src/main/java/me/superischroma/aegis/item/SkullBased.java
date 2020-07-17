package me.superischroma.aegis.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.UUID;

public class SkullBased extends AegisItem
{
    public SkullBased(String name, Rarity rarity)
    {
        super(name, Material.PLAYER_HEAD, rarity);
    }

    public void setSkullOwner(String name)
    {
        ((SkullMeta) meta).setOwner(name);
    }

    public void setSkullURL(String url)
    {
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] ed = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"http://textures.minecraft.net/texture/%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(ed)));
        Field f;
        try
        {
            f = ((SkullMeta) meta).getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set(meta, profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {}
    }

    @Override
    public void applyMetaToStack()
    {
        meta.setLore(getLore());
        getStack().setItemMeta(meta);
    }
}