package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.UI.MenuUI;
import tomer.prison.managers.MenuManager.Utils.ReadFromFile;

import java.io.FileNotFoundException;
import java.util.Objects;

public class MenuCommand implements CommandExecutor {

    public PrisonPlugin plugin;
    public MenuCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        plugin.getCommand("menu").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        int size;
        //if (size == 0){
//        try {
//            if (ReadFromFile.readLineFromFile(0) == null) {
//                player.sendMessage("The menu has not been initialized yet!");
//                return false;
//            } else{
//                size = Integer.parseInt(Objects.requireNonNull(ReadFromFile.readLineFromFile(0)));
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        player.sendMessage("Opens the menu!");
        try {
            player.openInventory(MenuUI.openInventory(player));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return false;
    }
}
