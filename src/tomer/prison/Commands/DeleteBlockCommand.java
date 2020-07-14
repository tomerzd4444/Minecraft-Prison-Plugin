package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.PrisonBlocksManager;

import java.util.ArrayList;

public class DeleteBlockCommand implements CommandExecutor {
    private final PrisonPlugin plugin;

    public DeleteBlockCommand(PrisonPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("deletecell").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // debug
        Player player = (Player) sender;
        player.sendMessage(player.getName() + " has used the delete block command!");
        player.sendMessage("args length: " + args.length);

        if (args.length == 1){

            // check if block exists
            PrisonBlocksManager prisonBlocksManager = new PrisonBlocksManager(plugin, PrisonPlugin.path);
            ArrayList<String> positions = prisonBlocksManager.getBlock(args[0], player);

            // debug
            player.sendMessage(String.valueOf(positions));

            if (positions == null){
                player.sendMessage("This block does not exist!");
                return false;
            }

            // removes block
            prisonBlocksManager.removeBlock(args[0],player);
        }
        return false;
    }
}