package me.superischroma.aegis.leveling;

import lombok.Getter;
import me.superischroma.aegis.User;
import me.superischroma.aegis.service.AegisService;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class LevelingListener extends AegisService
{
    @Override
    public void start()
    {
    }

    @Override
    public void stop()
    {
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        User user = User.getUser(e.getPlayer());
        Block block = e.getBlock();
        Material type = block.getType();
        BlockData data = block.getBlockData();
        XPBlock xpBlock = null;
        for (XPBlock b : XPBlock.values())
        {
            if (block.getType() == b.getType())
                xpBlock = b;
        }
        if (xpBlock == null)
            return;
        if (!e.isDropItems() && user.getPlayer().getGameMode() != GameMode.CREATIVE)
            return;
        if (type == Material.WHEAT_SEEDS)
        {
            if (((Ageable) data).getAge() == 7)
            {
                increase(user, xpBlock);
            }
            return;
        }
        increase(user, xpBlock);
    }

    public void increase(User user, XPBlock block)
    {
        user.play(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
        user.setLevelProgress(user.getLevelProgress() + block.getXP());
        user.sendActionBar(ChatColor.DARK_AQUA + "Level " + user.getLevel() + " [" + user.getLevelProgress() + "/" + user.getNextLevel() + " XP] " +
                ChatColor.GREEN + "+" + block.getXP());
        if (user.getLevelProgress() >= user.getNextLevel())
        {
            user.play(Sound.ENTITY_PLAYER_LEVELUP);
            user.sendActionBar(ChatColor.DARK_AQUA + "Level " + user.getLevel() + " -> Level " + (user.getLevel() + 1));
            user.setLevel(user.getLevel() + 1);
            user.setNextLevel((int) (user.getNextLevel() * 1.25));
        }
        user.save();
    }

    private enum XPBlock
    {
        WHEAT_SEEDS(1.0),
        COAL_ORE(1.5),
        REDSTONE_ORE(2.0),
        IRON_ORE(2.5),
        LAPIS_ORE(2.5),
        GOLD_ORE(3.0),
        DIAMOND_ORE(4.0),
        EMERALD_ORE(6.0);

        @Getter
        private final Material type;

        private final double xp;

        XPBlock(double xp)
        {
            this.type = Material.valueOf(name());
            this.xp = xp;
        }

        public double getXP()
        {
            return xp;
        }
    }
}