package me.superischroma.aegis.util;

import me.superischroma.aegis.item.ItemType;
import me.superischroma.aegis.item.variant.Variant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AUtil
{
    private static ItemStack blank = createNamedStack(Material.GRAY_STAINED_GLASS_PANE, ChatColor.DARK_GRAY + "");
    private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("MM/dd/yy");
    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm aa");
    private static NumberFormat COMMA_FORMAT = NumberFormat.getInstance();

    static
    {
        COMMA_FORMAT.setGroupingUsed(true);
    }

    public static String color(String s)
    {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getCurrentTime()
    {
        String result = TIME_FORMAT.format(new Date());
        if (result.startsWith("0"))
            result = result.substring(1);
        return result;
    }

    public static ItemStack createNamedStack(Material material, String name)
    {
        ModifyableItemStack stack = ModifyableItemStack.from(new ItemStack(material));
        stack.setName(name);
        stack.apply();
        return stack;
    }

    public static String getCurrentDate()
    {
        return DAY_FORMAT.format(new Date());
    }

    public static String getTimeState()
    {
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (h >= 22)
        {
            return "Late Night";
        }

        if (h >= 19)
        {
            return "Early Night";
        }

        if (h >= 15)
        {
            return "Late Afternoon";
        }

        if (h >= 12)
        {
            return "Early Afternoon";
        }

        if (h >= 10)
        {
            return "Late Morning";
        }

        if (h >= 5)
        {
            return "Early Morning";
        }

        return "Late Night";
    }

    public static String getBiome(Biome biome)
    {
        switch (biome)
        {
            case BADLANDS:
            case BADLANDS_PLATEAU:
            case WOODED_BADLANDS_PLATEAU:
            case MODIFIED_WOODED_BADLANDS_PLATEAU:
            case MODIFIED_BADLANDS_PLATEAU:
                return "Badlands";
            case BEACH:
            case SNOWY_BEACH:
            case STONE_SHORE:
                return "Beach";
            case OCEAN: return "Ocean";
            case RIVER: return "River";
            case SWAMP:
            case SWAMP_HILLS:
                return "Swamp";
            case TAIGA:
            case TAIGA_HILLS:
            case SNOWY_TAIGA:
            case SNOWY_TAIGA_HILLS:
            case GIANT_SPRUCE_TAIGA:
            case GIANT_TREE_TAIGA_HILLS:
            case GIANT_SPRUCE_TAIGA_HILLS:
                return "Taiga";
            case DESERT:
            case DESERT_HILLS:
            case DESERT_LAKES:
                return "Desert";
            case FOREST: return "Forest";
            case JUNGLE:
            case JUNGLE_EDGE:
            case JUNGLE_HILLS:
            case MODIFIED_JUNGLE:
            case MODIFIED_JUNGLE_EDGE:
                return "Jungle";
            case PLAINS:
            case SUNFLOWER_PLAINS:
                return "Plains";
            case SAVANNA:
            case SAVANNA_PLATEAU:
                return "Savanna";
            case THE_END: return "The End";
            case THE_VOID: return "Void";
            case MOUNTAINS:
            case MOUNTAIN_EDGE:
            case SNOWY_MOUNTAINS:
            case TAIGA_MOUNTAINS:
            case WOODED_MOUNTAINS:
            case GRAVELLY_MOUNTAINS:
            case SNOWY_TAIGA_MOUNTAINS:
            case MODIFIED_GRAVELLY_MOUNTAINS:
                return "Mountains";
            case COLD_OCEAN: return "Cold Ocean";
            case DEEP_OCEAN: return "Deep Ocean";
            case ICE_SPIKES: return "Ice Spikes";
            case WARM_OCEAN: return "Warm Ocean";
            case DARK_FOREST:
            case DARK_FOREST_HILLS:
                return "Dark Forest";
            case END_BARRENS: return "End Barrens";
            case BIRCH_FOREST:
            case BIRCH_FOREST_HILLS:
                return "Birch Forest";
            case END_MIDLANDS: return "End Midlands";
            case FROZEN_OCEAN: return "Frozen Ocean";
            case FROZEN_RIVER: return "Frozen River";
            case SNOWY_TUNDRA: return "Snowy Tundra";
            case WOODED_HILLS: return "Wooded Hills";
            case BAMBOO_JUNGLE:
            case BAMBOO_JUNGLE_HILLS:
                return "Bamboo Jungle";
            case BASALT_DELTAS: return "Basalt Deltas";
            case END_HIGHLANDS: return "End Highlands";
            case FLOWER_FOREST: return "Flower Forest";
            case NETHER_WASTES: return "Nether Wastes";
            case WARPED_FOREST: return "Warped Forest";
            case CRIMSON_FOREST: return "Crimson Forest";
            case LUKEWARM_OCEAN: return "Lukewarm Ocean";
            case DEEP_COLD_OCEAN: return "Deep Cold Ocean";
            case DEEP_WARM_OCEAN: return "Deep Warm Ocean";
            case ERODED_BADLANDS: return "Eroded Badlands";
            case MUSHROOM_FIELDS:
            case MUSHROOM_FIELD_SHORE:
                return "Mushroom Fields";
            case GIANT_TREE_TAIGA: return "Giant Tree Taiga";
            case SOUL_SAND_VALLEY: return "Soul Sand Valley";
            case TALL_BIRCH_HILLS: return "Tall Birch Hills";
            case DEEP_FROZEN_OCEAN: return "Deep Frozen Ocean";
            case SHATTERED_SAVANNA:
            case SHATTERED_SAVANNA_PLATEAU:
                return "Shattered Savanna";
            case SMALL_END_ISLANDS: return "End Islands";
            case TALL_BIRCH_FOREST: return "Tall Birch Forest";
            case DEEP_LUKEWARM_OCEAN: return "Deep Lukewarm Ocean";
            default: return "None";
        }
    }

    public static ChatColor getBiomeColor(Biome biome)
    {
        switch (biome)
        {
            case BADLANDS:
            case BADLANDS_PLATEAU:
            case WOODED_BADLANDS_PLATEAU:
            case MODIFIED_WOODED_BADLANDS_PLATEAU:
            case MODIFIED_BADLANDS_PLATEAU:
            case NETHER_WASTES:
            case ERODED_BADLANDS:
            case MUSHROOM_FIELDS:
            case MUSHROOM_FIELD_SHORE:
                return ChatColor.RED;
            case BEACH:
            case SNOWY_BEACH:
            case STONE_SHORE:
            case FLOWER_FOREST:
                return ChatColor.YELLOW;
            case OCEAN:
            case RIVER:
            case WARM_OCEAN:
                return ChatColor.BLUE;
            case SWAMP:
            case SWAMP_HILLS:
            case TAIGA:
            case TAIGA_HILLS:
            case GIANT_SPRUCE_TAIGA:
            case GIANT_TREE_TAIGA_HILLS:
            case GIANT_SPRUCE_TAIGA_HILLS:
            case JUNGLE:
            case JUNGLE_EDGE:
            case JUNGLE_HILLS:
            case MODIFIED_JUNGLE:
            case MODIFIED_JUNGLE_EDGE:
            case DARK_FOREST:
            case DARK_FOREST_HILLS:
            case GIANT_TREE_TAIGA:
                return ChatColor.DARK_GREEN;
            case SNOWY_TAIGA:
            case SNOWY_TAIGA_HILLS:
            case SNOWY_MOUNTAINS:
            case SNOWY_TAIGA_MOUNTAINS:
            case BIRCH_FOREST:
            case BIRCH_FOREST_HILLS:
            case TALL_BIRCH_FOREST:
            case TALL_BIRCH_HILLS:
            case SNOWY_TUNDRA:
                return ChatColor.WHITE;
            case DESERT:
            case DESERT_HILLS:
            case DESERT_LAKES:
            case SAVANNA:
            case SAVANNA_PLATEAU:
            case SHATTERED_SAVANNA:
            case SHATTERED_SAVANNA_PLATEAU:
                return ChatColor.GOLD;
            case FOREST:
            case PLAINS:
            case SUNFLOWER_PLAINS:
            case WOODED_HILLS:
            case BAMBOO_JUNGLE:
            case BAMBOO_JUNGLE_HILLS:
            case LUKEWARM_OCEAN:
                return ChatColor.GREEN;
            case THE_END:
            case END_BARRENS:
            case END_MIDLANDS:
            case END_HIGHLANDS:
            case SMALL_END_ISLANDS:
                return ChatColor.LIGHT_PURPLE;
            case MOUNTAINS:
            case MOUNTAIN_EDGE:
            case TAIGA_MOUNTAINS:
            case WOODED_MOUNTAINS:
            case GRAVELLY_MOUNTAINS:
            case MODIFIED_GRAVELLY_MOUNTAINS:
            case ICE_SPIKES:
            case WARPED_FOREST:
            case DEEP_WARM_OCEAN:
            case DEEP_LUKEWARM_OCEAN:
                return ChatColor.AQUA;
            case COLD_OCEAN:
            case DEEP_OCEAN:
            case FROZEN_OCEAN:
            case FROZEN_RIVER:
            case DEEP_COLD_OCEAN:
            case DEEP_FROZEN_OCEAN:
                return ChatColor.DARK_BLUE;
            case BASALT_DELTAS:
            case SOUL_SAND_VALLEY:
                return ChatColor.GRAY;
            case CRIMSON_FOREST: return ChatColor.DARK_RED;
            case THE_VOID: return ChatColor.DARK_GRAY;
            default: return ChatColor.AQUA;
        }
    }

    public static List<String> getPlayerList()
    {
        List<String> list = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
        {
            list.add(player.getName());
        }
        return list;
    }

    public static String colorize(String string)
    {
        Matcher matcher = Pattern.compile("&#[a-f0-9A-F]{6}").matcher(string);
        while (matcher.find())
        {
            String code = matcher.group().replace("&", "");
            string = string.replace("&" + code, net.md_5.bungee.api.ChatColor.of(code) + "");
        }

        string = ChatColor.translateAlternateColorCodes('&', string);
        return string;
    }

    public static String getStringAttribute(Attribute attr)
    {
        switch (attr)
        {
            case GENERIC_MAX_HEALTH: return "generic.max_health";
            case GENERIC_MOVEMENT_SPEED: return "generic.movement_speed";
            case GENERIC_ATTACK_DAMAGE: return "generic.attack_damage";
            case GENERIC_LUCK: return "generic.luck";
            case GENERIC_FOLLOW_RANGE: return "generic.follow_range";
            case GENERIC_ARMOR: return "generic.armor";
            case HORSE_JUMP_STRENGTH: return "horse.jump_strength";
            case GENERIC_ATTACK_SPEED: return "generic.attack_speed";
            case GENERIC_FLYING_SPEED: return "generic.flying_speed";
            case GENERIC_ARMOR_TOUGHNESS: return "generic.armor_toughness";
            case ZOMBIE_SPAWN_REINFORCEMENTS: return "zombie.spawn_reinforcements";
            case GENERIC_KNOCKBACK_RESISTANCE: return "generic.knockback_resistance";
            case GENERIC_ATTACK_KNOCKBACK: return "generic.attack_knockback";
            default: return "";
        }
    }

    public static String getStringEnchant(Enchantment enchantment)
    {
        switch (enchantment.getKey().getKey())
        {
            case "aqua_affinity": return "Aqua Affinity";
            case "bane_of_arthropods": return "Bane of Arthropods";
            case "binding_curse": return "Curse of Binding";
            case "blast_protection": return "Blast Protection";
            case "channeling": return "Channeling";
            case "depth_strider": return "Depth Strider";
            case "efficiency": return "Efficiency";
            case "feather_falling": return "Feather Falling";
            case "fire_aspect": return "Fire Aspect";
            case "fire_protection": return "Fire Protection";
            case "flame": return "Flame";
            case "fortune": return "Fortune";
            case "frost_walker": return "Frost Walker";
            case "impaling": return "Impaling";
            case "infinity": return "Infinity";
            case "knockback": return "Knockback";
            case "looting": return "Looting";
            case "loyalty": return "Loyalty";
            case "luck_of_the_sea": return "Luck of the Sea";
            case "lure": return "Lure";
            case "mending": return "Mending";
            case "multishot": return "Multishot";
            case "piercing": return "Piercing";
            case "power": return "Power";
            case "projectile_protection": return "Projectile Protection";
            case "protection": return "Protection";
            case "punch": return "Punch";
            case "quick_charge": return "Quick Charge";
            case "respiration": return "Respiration";
            case "riptide": return "Riptide";
            case "sharpness": return "Sharpness";
            case "silk_touch": return "Silk Touch";
            case "smite": return "Smite";
            case "sweeping": return "Sweeping Edge";
            case "thorns": return "Thorns";
            case "unbreaking": return "Unbreaking";
            case "vanishing_curse": return "Curse of Vanishing";
            default: return "Invalid Enchant";
        }
    }

    public static ItemStack getBlank()
    {
        return blank;
    }

    public static String getCommaSpacedLong(long l)
    {
        return COMMA_FORMAT.format((double) l);
    }

    // this is just sorta a precaution
    public static ItemStack singularify(ItemStack stack)
    {
        if (stack == null)
            return new ItemStack(Material.AIR);
        ItemStack c = stack.clone();
        c.setAmount(1);
        return c;
    }

    public static boolean isStackAir(ItemStack stack)
    {
        if (stack == null)
            return true;
        if (stack.isSimilar(new ItemStack(Material.AIR)))
            return true;
        return false;
    }

    public static Variant getRandomVariant(ItemType type)
    {
        switch (type)
        {
            case MELEE: return Groups.MELEE_VARIANTS.get(new Random().nextInt(Groups.MELEE_VARIANTS.size() - 1));
            case TOOLS: return Groups.TOOLS_VARIANTS.get(new Random().nextInt(Groups.TOOLS_VARIANTS.size() - 1));
            case RANGED: return Groups.RANGED_VARIANTS.get(new Random().nextInt(Groups.RANGED_VARIANTS.size() - 1));
        }
        return null;
    }

    public static ItemStack amount(ItemStack stack, int amount)
    {
        stack.setAmount(amount);
        return stack;
    }
}