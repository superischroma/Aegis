package me.superischroma.aegis.enchantment;

import me.superischroma.aegis.service.AegisService;
import me.superischroma.aegis.util.ALog;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;

public class AegisEnchantmentHandler extends AegisService
{
    @Override
    public void start()
    {
        for (AegisEnchantment enchantment : AegisEnchantment.values())
        {
            Enchantment e = enchantment.newInstance();
            if (e == null)
                continue;
            try
            {
                Field f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                Enchantment.registerEnchantment(e);
            }
            catch (Exception ex)
            {
                ALog.warn("Something went wrong while registering enchantment: " + enchantment.name());
            }
        }
    }

    @Override
    public void stop()
    {
    }
}