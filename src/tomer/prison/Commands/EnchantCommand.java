package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomer.prison.PrisonPlugin;
import tomer.prison.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EnchantCommand implements CommandExecutor, TabCompleter {
    private final PrisonPlugin plugin;

    public EnchantCommand(PrisonPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("enchant").setExecutor(this::onCommand);
        plugin.getCommand("enchant").setTabCompleter(this::onTabComplete);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length != 2){
            player.sendMessage("Should be 2 arguments!");
            return false;
        }
        ItemStack item = player.getItemInHand();
        String enchName = args[0].toUpperCase();
        String name = item.getType().toString();
        enchName = Utils.convertToEnch(enchName.toLowerCase());
        int level = Integer.parseInt(args[1]);
        if (level < 1){
            player.sendMessage("level is too low!");
            return false;
        } else if (level > 9999) {
            player.sendMessage("level is too high!");
            return false;
        }
        Enchantment ench = Enchantment.getByName(enchName);
        item.addUnsafeEnchantment(ench, level);
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        ArrayList<String> enchantments = Utils.getEnchantments();
        ArrayList<String> toReturn = new ArrayList<String>();
        if (args.length != 1) {
            return null;
        }
        String arg = args[0];
        if (arg.equalsIgnoreCase("")) {
            for (String val : enchantments) {
                toReturn.add(String.valueOf(val));
            }
        } else {
            for (String val : enchantments) {
                if (String.valueOf(val).startsWith(arg.toLowerCase())) {
                    toReturn.add(String.valueOf(val));
                }
            }
        }
        return toReturn;
    }
}
