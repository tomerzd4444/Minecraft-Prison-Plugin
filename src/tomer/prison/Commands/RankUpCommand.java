package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.User.PlayerYAMLUtil;
import tomer.prison.Utils.Utils;
import tomer.prison.managers.BalanceManager;

import java.util.ArrayList;
import java.util.Arrays;

public class RankUpCommand implements CommandExecutor {
    PrisonPlugin plugin;
    BalanceManager balanceManager;

    public RankUpCommand(PrisonPlugin plugin) {
        this.plugin = plugin;
        balanceManager = new BalanceManager(plugin, PrisonPlugin.path);
        plugin.getCommand("rankup").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        FileConfiguration config = PrisonPlugin.config;
        ArrayList<String> ranks = new ArrayList<>(config.getConfigurationSection("RANKUP").getKeys(true));
//        ConfigurationSection configurationSection = config.getConfigurationSection("RANKUP");
//        player.sendMessage(configurationSection.toString());
//
//        Map<String, Object> values = configurationSection.getValues(true);
//        player.sendMessage(values.toString());
//        player.sendMessage(values.keySet().toString());
//        for (String i : values.keySet()){
//            player.sendMessage("i: " + i);
//        }
        String currentRank = PlayerYAMLUtil.readFile(player).getRank();
        int balance = PlayerYAMLUtil.readFile(player).getBalance();
        player.sendMessage(Arrays.toString(new ArrayList[]{ranks}));
        int index = -1;
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i).equals(currentRank)) {
                index = i;
            }
        }
        if (index == -1) {
            player.sendMessage("An error has occurred");
            return false;
        }
        if (index == ranks.size() - 1) {
            player.sendMessage("You are at the highest rank!");
            return false;
        }
        String nextRank = ranks.get(index + 1);
        int nextRankCost = config.getInt("RANKUP." + nextRank);
        if (nextRankCost <= balance) {
            balanceManager.removeCurrencyFromPlayer(player, nextRankCost);
        } else {
            player.sendMessage("You don't have enough money!, you need " + nextRankCost + config.getString("BALANCE.SIGN"));
            return false;
        }
        PlayerYAMLUtil.readFile(player).setRank(nextRank, player.getUniqueId());
        player.sendMessage("You have successfully ranked up to rank " + nextRank);
        Utils.setBalScoreboard(player);


        return false;
    }
}
