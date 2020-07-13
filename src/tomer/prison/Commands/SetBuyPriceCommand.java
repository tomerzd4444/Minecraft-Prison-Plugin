package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomer.prison.PrisonPlugin;
import tomer.prison.UI.MenuUI;
import tomer.prison.managers.MenuManager.Utils.ReadFromFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetBuyPriceCommand implements CommandExecutor {

    public PrisonPlugin plugin;
    public SetBuyPriceCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        plugin.getCommand("setbuyprice").setExecutor(this::onCommand);
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
        player.sendMessage(String.valueOf(itemMeta));
        if (itemMeta == null){
            return false;
        }
        List<String> lore = itemMeta.getLore();
        List<String> buy = new ArrayList<String>();
        buy.add("Buy Price: " + args[0]);
        if (lore == null){
            itemMeta.setLore(buy);
        }else {
            List<String> newLore = lore.subList(0, 1);
            newLore.addAll(buy);
            if (lore.size() > 2) {
                newLore.addAll(lore.subList(2, lore.size()));
            }
            itemMeta.setLore(newLore);
        }
        player.sendMessage(String.valueOf(itemMeta.getLore()));

        item.setItemMeta(itemMeta);
        return false;
    }
}