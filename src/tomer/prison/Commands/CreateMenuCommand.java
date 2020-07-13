package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.MenuManager.SetMenuSize;
import tomer.prison.managers.MenuManager.Utils.WriteToFile;

import java.io.IOException;

public class CreateMenuCommand implements CommandExecutor {

    public PrisonPlugin plugin;
    public MenuCommand menuCommand;
    public CreateMenuCommand(PrisonPlugin plugin, MenuCommand menuCommand){
        this.plugin = plugin;
        this.menuCommand = menuCommand;
        plugin.getCommand("createmenu").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage(args);
        int size = Integer.parseInt(args[0]);
        if (size <= 0 || size % 9 != 0){
            sender.sendMessage("size should be dividable by 9 and greater than 0");
            return false;
        }
        //menuCommand.setSize(size);
        try {
            SetMenuSize.setSize(size,player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sender.sendMessage("successfully changed the menu's size!");
        return false;
    }
}
