package me.superischroma.aegis.command;

import me.superischroma.aegis.item.AegisInstanceItem;
import me.superischroma.aegis.item.variant.Variant;
import me.superischroma.aegis.rank.Rank;
import me.superischroma.aegis.user.User;
import me.superischroma.aegis.util.AUtil;
import me.superischroma.aegis.util.CompactEnchantment;
import org.bukkit.inventory.ItemStack;

@CommandParameters(description = "Item instancing test command.", rank = Rank.SUPER, source = CommandSource.IN_GAME)
public class Command_afrom extends AegisCommand
{
    @Override
    public void run(CommandUser sender, User user, String[] args)
    {
        checkArgs(args.length != 0);
        ItemStack stack = sender.getPlayer().getInventory().getItemInMainHand();
        if (AUtil.isStackAir(stack))
            throw new CommandFailException("itemInHandNeeded");
        AegisInstanceItem aii = AegisInstanceItem.from(stack);
        if (aii == null)
            throw new CommandFailException("itemInstanceNotFound");
        send("Item details: ");
        Variant variant = aii.getVariant();
        if (variant != null)
            send(" - Variant: " + variant.getName());
        send(" - Enchantments: ");
        for (CompactEnchantment enchantment : aii.getEnchantments())
        {
            send("   - " + AUtil.getStringEnchant(enchantment.getEnchantment()) + " " + enchantment.getLevel());
        }
        send(" - Item: " + aii.getItem().getRawName());
    }
}