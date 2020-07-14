package tomer.prison;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import tomer.prison.Commands.*;
import tomer.prison.Debuger.User;
import tomer.prison.Listeners.BlockBreakListener;
import tomer.prison.Listeners.InventoryClickListener;
import tomer.prison.Listeners.PlayerJoinListener;
import tomer.prison.UI.MenuUI;
import tomer.prison.Utils.Utils;
import tomer.prison.managers.BalanceManager;
import tomer.prison.managers.MenuManager.Menu;
import tomer.prison.managers.PrisonBlocksManager;

import java.io.IOException;

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
    }

    private void loadListeners() {
        new BlockBreakListener(this);
        new PlayerJoinListener(this);
        new InventoryClickListener(this);
    }

    private void loadManagers() {
        User.sendOwnerMessage(path);
        balanceManager = new BalanceManager(this, path);
        prisonBlocksManager = new PrisonBlocksManager(this, path);
        menu = new Menu();
    }

    @Override
    public void onEnable() {
        path = this.getConfig().getString("PATH");
        saveConfig();
        loadCommands();
        loadListeners();
        loadManagers();
        new Utils(this);
        config = this.getConfig();
        PluginDescriptionFile pdf = this.getDescription(); //Gets plugin.yml
        getLogger().info("Prison Plugin is now running!");
        Bukkit.broadcastMessage(Utils.chat("&l&3Prison Plugin is now running!"));
//        Player owner = null;
//        for (Player i : players){
//            getLogger().info(i.getName());
//            if (i.getName().equalsIgnoreCase("blueblood4444")){
//                i.sendMessage(Utils.chat("&l&3Prison Plugin is now running on version " + pdf.getVersion() + "!"));
//                owner = i;
//            }
//        }
        Player owner = User.getOwner();
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
        try {
            balanceManager.saveCurrencyFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getLogger().info("Prison Plugin is now disabled!");
    }


}
