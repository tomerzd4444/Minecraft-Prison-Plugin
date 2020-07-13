package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomer.prison.PrisonPlugin;

import java.util.ArrayList;
import java.util.List;

public class SetSellPriceCommand implements CommandExecutor {

    public PrisonPlugin plugin;
    public SetSellPriceCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        plugin.getCommand("setsellprice").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length != 1){
            player.sendMessage("You need 1 argument!");
            return false;
        }
        ItemStack item = player.getItemInHand();
        player.sendMessage(String.valueOf(item));
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null){
            return false;
        }
        List<String> lore = itemMeta.getLore();
        List<String> sell = new ArrayList<String>();
        sell.add("Sell Price: " + args[0]);
        if (lore == null){
            itemMeta.setLore(sell);
        }else {
            List<String> newLore = sell;
            if (lore.size() > 1) {
                newLore.addAll(lore.subList(2, lore.size()));
            }
            itemMeta.setLore(newLore);

        }
        player.sendMessage(String.valueOf(itemMeta.getLore()));
        item.setItemMeta(itemMeta);
        return false;
    }
}
