package me.superischroma.aegis.command;

import me.superischroma.aegis.enchantment.AegisEnchantment;
import me.superischroma.aegis.mob.AegisMob;
import me.superischroma.aegis.mob.AegisMobType;
import me.superischroma.aegis.user.User;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandParameters(description = "Get an enchantment book from Aegis.", usage = "/<command> <enchant> [level]", source = CommandSource.IN_GAME)
public class Command_agetbook extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args) throws Exception
    {
        checkArgs(args.length < 1 || args.length > 2);
        int level = 1;
        if (args.length == 2)
            level = parseInt(args[1]);
        AegisEnchantment enchantment = AegisEnchantment.findEnchantment(args[0]);
        if (enchantment == null)
            throw new Exception("Invalid enchantment.");
        Enchantment e = enchantment.newInstance();
        if (e == null)
            throw new Exception("Something went wrong while creating this book!");
        ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) stack.getItemMeta();
        meta.setLore(Collections.singletonList(ChatColor.GRAY + AUtil.getStringEnchant(e) + " " + level));
        meta.addStoredEnchant(e, level, true);
        stack.setItemMeta(meta);
        PlayerInventory inv = sender.getPlayer().getInventory();
        if (inv.firstEmpty() > inv.getSize() - 1)
            throw new Exception("No space in inventory.");
        inv.setItem(inv.firstEmpty(), stack);
        send("Gave you a book with " + AUtil.getStringEnchant(e) + " " + level + ".");
    }

    @Override
    public List<String> getTabCompleteOptions(CommandUser user, String[] args)
    {
        if (args.length == 1)
        {
            List<String> list = new ArrayList<>();
            for (AegisEnchantment enchantment : AegisEnchantment.values())
            {
                list.add(enchantment.name());
            }
            return list;
        }
        return null;
    }
}