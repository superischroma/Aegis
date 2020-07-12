package me.superischroma.aegis.item;

import lombok.Getter;
import lombok.Setter;
import me.superischroma.aegis.gui.GUI;
import me.superischroma.aegis.item.variant.Variant;
import me.superischroma.aegis.item.variant.VariantType;
import me.superischroma.aegis.util.AUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AegisItem
{
    private static String mark = ChatColor.DARK_GRAY + "aegis";

    // the actual stack that is used in the game
    @Getter
    private ItemStack stack;

    // the stack's meta
    protected ItemMeta meta;

    // the stack's lore
    private List<String> lore;

    // raw name
    @Getter
    private String rawName;

    // enchantable?
    @Getter
    private boolean enchantable;

    // enchantment color
    @Getter
    @Setter
    private ChatColor enchantmentColor;

    // rarity
    @Getter
    private Rarity rarity;

    // gui
    private GUI gui;

    // recipe
    private Recipe recipe;

    // type
    @Getter
    private ItemType type;

    public AegisItem(String name, Material type, Rarity rarity, ItemType itemType)
    {
        this.stack = new ItemStack(type);
        this.rarity = rarity;
        this.enchantmentColor = ChatColor.BLUE;
        this.type = itemType;
        this.meta = stack.getItemMeta();
        this.lore = new ArrayList<>();
        this.rawName = name;
        this.enchantable = false;
        this.setName(rarity.getColor() + name);
        this.meta.setUnbreakable(true);
        this.meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        lore.add(rarity.getName());
        lore.add(mark);
        this.applyMetaToStack();
    }

    public AegisItem(String name, Material type, Rarity rarity)
    {
        this(name, type, rarity, ItemType.NONE);
    }

    public void setName(String name)
    {
        meta.setDisplayName(name);
    }

    public String getName()
    {
        return meta.getDisplayName();
    }

    public void applyMetaToStack()
    {
        meta.setLore(lore);
        stack.setItemMeta(meta);
    }

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation, EquipmentSlot slot)
    {
        meta.addAttributeModifier(attr, new AttributeModifier(UUID.randomUUID(), AUtil.getStringAttribute(attr), amount, operation, slot));
    }

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation)
    {
        addAttribute(attr, amount, operation, null);
    }

    public void addAttribute(Attribute attr, double amount, EquipmentSlot slot)
    {
        addAttribute(attr, amount, AttributeModifier.Operation.ADD_NUMBER, slot);
    }

    public void enchantable()
    {
        this.enchantable = true;
        lore.remove(rarity.getName());
        lore.remove(mark);
        lore.add(enchantmentColor + "No Enchantments");
        lore.add(rarity.getName());
        lore.add(mark);
    }

    public void glow()
    {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }

    public void addLoreLine(String s)
    {
        lore.remove(enchantmentColor + "No Enchantments");
        lore.remove(rarity.getName());
        lore.remove(mark);
        lore.add(AUtil.colorize(s));
        if (enchantable)
            lore.add(enchantmentColor + "No Enchantments");
        lore.add(rarity.getName());
        lore.add(mark);
    }

    public GUI getGUI()
    {
        return gui;
    }

    public void setGUI(GUI gui)
    {
        this.gui = gui;
    }

    public boolean hasGUI()
    {
        return gui != null;
    }

    public Recipe getRecipe()
    {
        return recipe;
    }

    public void setRecipe(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public boolean hasRecipe()
    {
        return recipe != null;
    }

    public void onBlockInteract(PlayerInteractEvent e) {}
    public void onItemInteract(PlayerInteractEvent e) {}

    public static boolean isValid(ItemStack stack, AegisItemType type)
    {
        if (stack == null)
            return false;
        if (!stack.hasItemMeta())
            return false;
        if (!stack.getItemMeta().hasLore())
            return false;
        if (!stack.getItemMeta().hasDisplayName())
        if (!stack.getItemMeta().getLore().contains(mark))
            return false;
        String name = stack.getItemMeta().getDisplayName();
        for (VariantType vtype : VariantType.values())
        {
            if (ChatColor.stripColor(name).startsWith(vtype.newInstance().getName()))
            {
                List<String> spl = new ArrayList<>(Arrays.asList(name.split(" ")));
                spl.remove(0);
                name = StringUtils.join(spl, " ");
            }
        }
        return name.contains(type.newInstance().getRawName());
    }

    public static AegisItem from(ItemStack stack)
    {
        AegisItem item = null;
        for (AegisItemType type : AegisItemType.values())
        {
            if (isValid(stack, type))
                item = type.newInstance();
        }
        return item;
    }

    public static double getHealthOffItem(ItemStack stack)
    {
        for (AegisItemType type : AegisItemType.values())
        {
            if (isValid(stack, type))
            {
                AegisItem item = type.newInstance();
                if (!(item instanceof Armor))
                    continue;
                return ((Armor) item).getHealth();
            }
        }
        return 0.0;
    }

    public static int getDefenseOffItem(ItemStack stack)
    {
        for (AegisItemType type : AegisItemType.values())
        {
            if (isValid(stack, type))
            {
                AegisItem item = type.newInstance();
                if (!(item instanceof Armor))
                    continue;
                return ((Armor) item).getDefense();
            }
        }
        return 0;
    }

    public static String getMark()
    {
        return mark;
    }
}