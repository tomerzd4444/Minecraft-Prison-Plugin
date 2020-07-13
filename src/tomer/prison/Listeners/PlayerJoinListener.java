package tomer.prison.Listeners;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.BalanceManager;
import tomer.prison.Utils.Utils;

public class PlayerJoinListener implements Listener {
    private PrisonPlugin plugin;
    private FileConfiguration config;
    private BalanceManager balanceManager;
    public PlayerJoinListener(PrisonPlugin plugin){
        this.plugin = plugin;
        this.balanceManager = new BalanceManager(plugin);
        config = plugin.getConfig();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        Utils.setBalScoreboard(player);
    }

}
