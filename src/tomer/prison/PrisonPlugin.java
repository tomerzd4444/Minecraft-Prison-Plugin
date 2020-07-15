package tomer.prison;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import tomer.prison.Commands.*;
import tomer.prison.Debuger.Debugger;
import tomer.prison.Listeners.BlockBreakListener;
import tomer.prison.Listeners.InventoryClickListener;
import tomer.prison.Listeners.PlayerJoinListener;
import tomer.prison.UI.MenuUI;
import tomer.prison.User.PlayerYAMLUtil;
import tomer.prison.Utils.Utils;
import tomer.prison.managers.BalanceManager;
import tomer.prison.managers.MenuManager.Menu;
import tomer.prison.managers.PrisonBlocksManager;

import java.io.File;

public class PrisonPlugin extends JavaPlugin {
    BalanceManager balanceManager;
    public static String path;
    public static FileConfiguration config;
    PrisonBlocksManager prisonBlocksManager;
    Menu menu;

    private void loadCommands() {
        new CreateBlockCommand(this);
        new DeleteBlockCommand(this);
        new SetBlockCommand(this);
        new EnchantCommand(this);
        MenuCommand menuCommand = new MenuCommand(this);
        new CreateMenuCommand(this, menuCommand);
        new BalanceCommand(this);
        new SetSellPriceCommand(this);
        new SetBuyPriceCommand(this);
        new SellCommand(this);
        new FlyCommand(this);
        new RankUpCommand(this);
    }

    private void loadListeners() {
        new BlockBreakListener(this);
        new PlayerJoinListener(this);
        new InventoryClickListener(this);
    }

    private void loadManagers() {
        balanceManager = new BalanceManager(this, path);
        prisonBlocksManager = new PrisonBlocksManager(this, path);
        menu = new Menu();
    }

    private void loadUtils() {
        new Utils(this);
        new PlayerYAMLUtil(this, path);
    }

    @Override
    public void onEnable() {
        this.saveConfig();
        path = this.getConfig().getString("PATH");
        if (path == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(Utils.chat("An Error has occurred while loading the plugin"));
            }
            return;
        }
        String pathname = path.substring(0, path.length() - 18) + "config.yml";
        Debugger.sendOwnerMessage(pathname);
        File file = new File(pathname);
        config = YamlConfiguration.loadConfiguration(file);
        loadCommands();
        loadListeners();
        loadManagers();
        loadUtils();
        // config = this.getConfig();
        PluginDescriptionFile pdf = this.getDescription(); //Gets plugin.yml
        getLogger().info("Prison Plugin is now running!");
        //Bukkit.broadcastMessage(Utils.chat("&l&3Prison Plugin is now running!"));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(Utils.chat("&l&3Prison Plugin is now running on version v" + pdf.getVersion() + "!"));
        }
//        Player owner = null;
//        for (Player i : players){
//            getLogger().info(i.getName());
//            if (i.getName().equalsIgnoreCase("blueblood4444")){
//                i.sendMessage(Utils.chat("&l&3Prison Plugin is now running on version " + pdf.getVersion() + "!"));
//                owner = i;
//            }
//        }
        Player owner = Debugger.getOwner();
        try {
            balanceManager.loadCurrencyFile();
            menu.initialize(this, path);
            MenuUI.initialize(this, owner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
//        try {
//            balanceManager.saveCurrencyFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        getLogger().info("Prison Plugin is now disabled!");
    }


}
