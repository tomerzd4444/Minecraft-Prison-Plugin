package tomer.prison.UI;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomer.prison.PrisonPlugin;
import tomer.prison.Utils.Utils;
import tomer.prison.managers.MenuManager.Utils.ReadFromFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MenuUI {
    public static Inventory inv;
    public static String inventory_name;
    public static int inv_rows;

    public static void initialize(PrisonPlugin plugin, Player player) throws FileNotFoundException {
        inventory_name = Utils.chat("&6&lMenu");
        FileConfiguration config = plugin.getConfig();
        inv_rows = config.getConfigurationSection("MENU").getInt("SIZE");
        inv = Bukkit.createInventory(null, inv_rows, inventory_name);
    }

    public static Inventory openInventory (Player player) throws FileNotFoundException {
        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inventory_name);
        //Utils.createItem(inv, Material.DIAMOND, 1, 0, "&cTest Item", "&7This is lore line 1", "&bSecond line", "&3Third line");
        try {
            List<String> items = ReadFromFile.readLinesFromFile();
            items = items.subList(1, items.size());
            ArrayList<String[]> argItems = new ArrayList<>();
            for (String i : items) {
                argItems.add(Utils.stringToArrayList(i));
            }
            for (String[] i : argItems) {
                String displayName = WordUtils.capitalize(i[0].replace("_", " "));
                Material mat = Material.matchMaterial(i[0]);
                ItemStack addItem = Utils.createItem(inv,mat, Integer.parseInt(i[1]), Integer.parseInt(i[2]), displayName, i[3], i[4]);
                ItemMeta addItemMeta = addItem.getItemMeta();
                addItemMeta.setDisplayName(displayName);
                inv.setItem(Integer.parseInt(i[2]),addItem);
            }
        } catch (Exception e) {
            player.sendMessage(String.valueOf(e));
        }
        toReturn.setContents(inv.getContents());
        return toReturn;
    }
}
