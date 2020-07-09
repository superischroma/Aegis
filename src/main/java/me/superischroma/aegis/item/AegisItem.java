package me.superischroma.aegis.item;

import lombok.Getter;
import lombok.Setter;
import me.superischroma.aegis.gui.GUI;
import me.superischroma.aegis.item.variant.Variant;
import me.superischroma.aegis.util.AUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

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

    // enchantment color
    @Setter
    private ChatColor enchantmentColor;

    // rarity
    private Rarity rarity;

    // gui
    private GUI gui;

    // recipe
    private Recipe recipe;

    // type
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
        this.setName(rarity.getColor() + name);
        this.meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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

    public void addAttribute(Attribute attr, double amount, AttributeModifier.Operation operation)
    {
        meta.addAttributeModifier(attr, new AttributeModifier(AUtil.getStringAttribute(attr), amount, operation));
    }

    public void addAttribute(Attribute attr, double amount)
    {
        addAttribute(attr, amount, AttributeModifier.Operation.ADD_NUMBER);
    }

    public void addEnchant(Enchantment enchantment, int level)
    {
        addLoreLine(enchantmentColor + AUtil.getStringEnchant(enchantment) + " " + level);
        meta.addEnchant(enchantment, level, true);
    }

    public void glow()
    {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
    }

    public void addLoreLine(String s)
    {
        lore.remove(rarity.getName());
        lore.remove(mark);
        lore.add(AUtil.colorize(s));
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

    public static boolean isValid(ItemStack stack, AegisItemType type)
    {
        if (!stack.hasItemMeta())
            return false;
        if (!stack.getItemMeta().hasLore())
            return false;
        if (!stack.getItemMeta().hasDisplayName())
        if (!stack.getItemMeta().getLore().contains(mark))
            return false;
        return stack.getItemMeta().getDisplayName().contains(type.newInstance().getRawName());
    }
}