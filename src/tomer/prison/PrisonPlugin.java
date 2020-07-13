package tomer.prison;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import tomer.prison.Listeners.BlockBreakListener;
import tomer.prison.Listeners.InventoryClickListener;
import tomer.prison.Listeners.PlayerJoinListener;
import tomer.prison.Commands.*;
import tomer.prison.UI.MenuUI;
import tomer.prison.managers.*;
import tomer.prison.managers.MenuManager.Menu;
import tomer.prison.Utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;

public class PrisonPlugin extends JavaPlugin {
    BalanceManager balanceManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        PrisonBlocksManager prisonBlocksManager = new PrisonBlocksManager(this);
        balanceManager = new BalanceManager(this);
        MenuCommand menuCommand = new MenuCommand(this);
        Menu menu = new Menu();
        new CreateMenuCommand(this,menuCommand);
        new Utils(this);
        new CreateBlockCommand(this);
        new DeleteBlockCommand(this);
        new SetBlockCommand(this);
        new EnchantCommand(this);
        new BlockBreakListener(this);
        new BalanceCommand(this);
        new SetSellPriceCommand(this);
        new SetBuyPriceCommand(this);
        new PlayerJoinListener(this);
        new InventoryClickListener(this);
        new SellCommand(this);
        PluginDescriptionFile pdf = this.getDescription(); //Gets plugin.yml
        getLogger().info("Prison Plugin is now running!");
        Bukkit.broadcastMessage(Utils.chat("&l&3Prison Plugin is now running!"));
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        Player owner = null;
        for (Player i : players){
            getLogger().info(i.getName());
            if (i.getName().equalsIgnoreCase("blueblood4444")){
                i.sendMessage(Utils.chat("&l&3Prison Plugin is now running on version " + pdf.getVersion() + "!"));
                owner = i;
            }
        }
        try {
            balanceManager.loadCurrencyFile();
            menu.setPlugin(this);
            MenuUI.initialize(this,owner);
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
