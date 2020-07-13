package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomer.prison.PrisonPlugin;
import tomer.prison.Utils.Utils;

public class SellCommand implements CommandExecutor {
    PrisonPlugin plugin;
    public SellCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        plugin.getCommand("sell").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage(label);
        player.sendMessage(String.valueOf(cmd));
        if (args.length != 1){
            player.sendMessage("Needs 1 argument!");
            return false;
        }
        int invSlot = -1;
        Inventory inv = player.getInventory();
        ItemStack item = player.getInventory().getItemInMainHand();
        for (int i = 0; i <= 8; i++){
            if (inv.getItem(i) == null) {
                continue;
            }
            if (inv.getItem(i).equals(item)) {
                player.sendMessage(String.valueOf(item));
                invSlot = i;
                break;
            }
        }
        if (args[0].equalsIgnoreCase("hand")){
            Utils.sell(player,invSlot);
        }else if (args[0].equalsIgnoreCase("all")){
            for (int i = 0; i <= 35; i ++){
                Utils.sell(player,i);
            }
        }
        return false;
    }
}
