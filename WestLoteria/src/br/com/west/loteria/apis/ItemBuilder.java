package br.com.west.loteria.apis;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import java.util.Arrays;

public class ItemBuilder {


    public static ItemStack add(String owner, String title, String... lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
        item.setDurability((short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(title);
        skull.setOwner(owner);
        skull.setLore(Arrays.asList(lore));
        item.setItemMeta((ItemMeta) skull);
        return item;

    }

    public static ItemStack add(Material m, String title, String... lore) {
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        meta.setDisplayName(title);
        item.setItemMeta(meta);
        return item;
    }

}
