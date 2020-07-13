package tomer.prison.managers.MenuManager.Utils;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddItem {
    public static void addItem(Inventory inv, ItemStack item) throws IOException {
        ItemMeta meta = item.getItemMeta();
        String displayName = item.getType().name().toLowerCase();//.replace("_", " ").toLowerCase();
        List<String> lore = meta.getLore();
        int amount = item.getAmount();
        int invSlot = 0;
        ItemStack[] items = inv.getContents();
        for (int i = 0; i < items.length; i ++){
            ItemStack itemCheck = items[i];
            if (itemCheck == item){
                invSlot = i;
                break;
            }
        }
        if (lore == null){
            lore = new ArrayList<String>();
            lore.add("");
            lore.add("");
        }
        if (lore.size() == 1){
            lore.add("");
        }
        WriteToFile.writeToFile(displayName, amount, invSlot, lore.get(0), lore.get(1));

    }
    public static void addItem(Inventory inv, ItemStack item, int invSlot) throws IOException {
        ItemMeta meta = item.getItemMeta();
        String displayName = item.getType().name().toLowerCase();//.replace("_", " ").toLowerCase();
        List<String> lore = meta.getLore();
        int amount = item.getAmount();
        if (lore == null){
            lore = new ArrayList<String>();
            lore.add("");
            lore.add("");
        }
        if (lore.size() == 1){
            lore.add("");
        }
        WriteToFile.writeToFile(displayName, amount, invSlot, lore.get(0), lore.get(1));

    }
}
