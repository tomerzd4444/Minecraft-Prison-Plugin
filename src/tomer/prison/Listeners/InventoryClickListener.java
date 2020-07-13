package tomer.prison.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import tomer.prison.PrisonPlugin;
import tomer.prison.UI.MenuUI;
import tomer.prison.Utils.Utils;
import tomer.prison.managers.MenuManager.Utils.AddItem;

import java.io.IOException;
import java.util.Objects;

public class InventoryClickListener implements Listener {

    private PrisonPlugin plugin;
    public InventoryClickListener(PrisonPlugin plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    //    @EventHandler
//    public void onClick(InventoryClickEvent e){
//        String title = e.getView().getTitle();
//        if (title.equals(TestUI.inventory_name)){
//            e.setCancelled(true);
//            if (e.getCurrentItem() == null){
//                return;
//            }
//            if (title.equals(TestUI.inventory_name)){
//                TestUI.clicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory());
//            }
//        }
//    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        assert item != null;
        String itemName = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
        String title = event.getView().getTitle();

        if (title.equals(MenuUI.inventory_name)){
            //event.setCancelled(true);
            //player.closeInventory();
//            if (event.getCurrentItem() == null){
//                return;
//            }
            int slot = event.getRawSlot();
            try {
                AddItem.addItem(MenuUI.inv, item, slot);
            } catch (IOException e) {
                System.out.println(e);
            }
            // RankUI.clicked((Player) event.getWhoClicked(), event.getSlot(), event.getCurrentItem(), event.getInventory());

        }
    }
}
