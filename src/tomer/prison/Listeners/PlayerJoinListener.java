package tomer.prison.Listeners;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tomer.prison.PrisonPlugin;
import tomer.prison.User.PlayerYAMLUtil;
import tomer.prison.Utils.Utils;
import tomer.prison.managers.BalanceManager;

public class PlayerJoinListener implements Listener {
    private final PrisonPlugin plugin;
    private final FileConfiguration config;
    private final BalanceManager balanceManager;

    public PlayerJoinListener(PrisonPlugin plugin) {
        this.plugin = plugin;
        this.balanceManager = new BalanceManager(plugin, PrisonPlugin.path);
        config = plugin.getConfig();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPlayedBefore()) {
            balanceManager.setPlayerCurrency(player, 0);
        }
        if (!PlayerYAMLUtil.exists(player) || !player.hasPlayedBefore()) {
            PlayerYAMLUtil.createPlayerFile(player);
        }
        Utils.setBalScoreboard(player);
    }

}
