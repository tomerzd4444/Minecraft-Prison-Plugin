package tomer.prison.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.PrisonBlocksManager;

import java.util.ArrayList;
import java.util.List;

public class CreateBlockCommand implements CommandExecutor, TabCompleter {
    private final PrisonPlugin plugin;
    public CreateBlockCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        // checks the command name and set executors
        plugin.getCommand("createcell").setExecutor(this::onCommand);
        plugin.getCommand("createcell").setTabCompleter(this::onTabComplete);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // sets the player
        Player player = (Player) sender;

        // debug
        player.sendMessage(player.getName() + " has used the create block command!");
        player.sendMessage("args length: " + args.length);

        if (args.length == 7){
            // gets the block's position
            PrisonBlocksManager prisonBlocksManager = new PrisonBlocksManager(plugin, PrisonPlugin.path);
            ArrayList<String> positions = prisonBlocksManager.getBlock(args[0], player);

            // debug
            player.sendMessage(String.valueOf(positions));
            player.sendMessage(String.valueOf(positions == null));

            // checks if block exists
            if (positions != null){
                player.sendMessage("This block name is already taken!");
                return false;
            }

            // sets the block
            prisonBlocksManager.setBlock(args[0],args[1],args[2],args[3],args[4],args[5],args[6],player);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // sets variables
        Player player = (Player) sender;
        ArrayList<String> coordinates = new ArrayList<String>();

        // check command
        if (cmd.getName().equalsIgnoreCase("createcell")){

            // sets variables
            Location loc = player.getLocation();
            String[] pos = new String[]{String.valueOf((int) loc.getX()), String.valueOf((int) loc.getY()), String.valueOf((int) loc.getZ())};
            int len = args.length;

            // checks length of arguments
            if (len == 1 || len >= 8){
                return null;
            }

            // debug
            player.sendMessage("args length " + len);
            player.sendMessage("args: " + args.toString());

            // gets position
            String p = pos[(len-2) % 3];
            // gets argument
            String arg = args[len-1];

            // checks if argument is none
            if (arg.equalsIgnoreCase("")) {
                coordinates.add(p);
            }else {
                if (p.startsWith(arg)){
                    coordinates.add(p);
                }
            }

            // debug
            player.sendMessage("coordinates " + coordinates);

            return coordinates;
        }
        return null;
    }
}
