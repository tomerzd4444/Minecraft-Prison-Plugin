package tomer.prison.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.BalanceManager;
import tomer.prison.Utils.Utils;

public class BalanceCommand implements CommandExecutor {
    private PrisonPlugin plugin;
    private BalanceManager balanceManager;

    public BalanceCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        this.balanceManager = new BalanceManager(plugin);
        // checks the command name and set executors
        plugin.getCommand("balance").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0){
            OfflinePlayer p = Bukkit.getOfflinePlayer(sender.getName());
            int value = balanceManager.getPlayerCurrency(p);
            sender.sendMessage(Utils.chat("&2You have &6" + value + "$&2!"));
            // sender.sendMessage(Utils.chat("&e/currency"));
        }
        if (args.length == 1 && (sender.hasPermission("prisonPlugin.currencymanager.useOthers") || sender.hasPermission("prisonPlugin.all"))){
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
            int value = balanceManager.getPlayerCurrency(p);
            sender.sendMessage(Utils.chat("&2The user &3" + args[0] + "&2 have &6" + value + "$&2!"));
        } else if (!(sender.hasPermission("prisonPlugin.useOthers") || sender.hasPermission("prisonPlugin.all"))){
            sender.sendMessage(Utils.chat("&cYou do not have the permission to use this command."));
        }
        if (args.length == 2){
            sender.sendMessage(Utils.chat("&e arguments can be 0, 1 and 3, not 2."));
        }
        if (args.length == 3 && (sender.hasPermission("prisonPlugin.currencymanager.change") || sender.hasPermission("prisonPlugin.all"))){
            // add, remove, set
            OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
            String command = args[1];
            int amount = Integer.parseInt(args[2]);;
            if (command.equalsIgnoreCase("add")){
                if (p.hasPlayedBefore()) {
                    balanceManager.addCurrencyToPlayer(p, amount);
                    sender.sendMessage(Utils.chat("&2You successfully added &6" + amount + "$ &2to &3" + args[0]));
                }
            }else if (command.equalsIgnoreCase("remove")){
                if (p.hasPlayedBefore()) {
                    balanceManager.removeCurrencyFromPlayer(p, amount);
                    sender.sendMessage(Utils.chat("&2You successfully removed &6" + amount + "$ &2from &3" + args[0]));
                }
            } else if (command.equalsIgnoreCase("set")){
                if (p.hasPlayedBefore()) {
                    balanceManager.setPlayerCurrency(p, amount);
                    sender.sendMessage(Utils.chat("&2You successful set &3" + args[0] + "&2 balance to &6" + amount + "$"));
                }
            } else{
                sender.sendMessage(Utils.chat("&4'" + command + "' is not a valid argument."));
            }
        }else if (!(sender.hasPermission("prisonPlugin.currencymanager.change") || sender.hasPermission("prisonPlugin.all"))){
            sender.sendMessage(Utils.chat("&cYou do not have the permission to use this command."));
        }
        return false;
    }
}
