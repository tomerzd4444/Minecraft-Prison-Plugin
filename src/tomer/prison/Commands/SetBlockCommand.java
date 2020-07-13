package tomer.prison.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.PrisonBlocksManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SetBlockCommand implements CommandExecutor, TabCompleter {
    private PrisonPlugin plugin;
    private FileConfiguration config;
    public SetBlockCommand(PrisonPlugin plugin){
        this.plugin = plugin;
        // checks the command name and set executors
        config = plugin.getConfig();
        plugin.getCommand("setcell").setExecutor(this::onCommand);
        plugin.getCommand("setcell").setTabCompleter(this::onTabComplete);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("You used the set cell command!");
        String name = args[0];
        PrisonBlocksManager prisonBlocksManager = new PrisonBlocksManager(plugin);
        ArrayList<String> positions = prisonBlocksManager.getBlock(name,player);
        if (positions == null){
            player.sendMessage("This prison block does not exist!");
            return false;
        }
        player.sendMessage("here");
        String[] percentages = args[2].split(",");
        ArrayList<Integer> percent = new ArrayList<Integer>();
        player.sendMessage(percentages);
        int total = 0;
        for (String i : percentages){
            percent.add(Integer.parseInt(i) + total);
            total += Integer.parseInt(i);
        }
        player.sendMessage("here2");
        if (total != 100){
            player.sendMessage("The percentages don't add up to 100%!");
            return false;
        }
        player.sendMessage("here3");
        String[] blocks = args[1].split(",");
        player.sendMessage(blocks);
        if (blocks.length != percentages.length){
            player.sendMessage("blocks and percentages don't have the same length!");
            return false;
        }
        player.sendMessage("here4");
        for (String i : blocks){
            try {
                Material.matchMaterial(i.toUpperCase());
            }catch (Exception e){
                player.sendMessage(String.valueOf(e));
                return false;
            }
        }
        player.sendMessage("here5");
        int xStart = Integer.parseInt(positions.get(0));
        int yStart = Integer.parseInt(positions.get(1));
        int zStart = Integer.parseInt(positions.get(2));
        int xEnd = Integer.parseInt(positions.get(3));
        int yEnd = Integer.parseInt(positions.get(4));
        int zEnd = Integer.parseInt(positions.get(5));
        int temp;
        if (xStart > xEnd){
            temp = xStart;
            xStart = xEnd;
            xEnd = temp;
        }
        if (yStart > yEnd){
            temp = yStart;
            yStart = yEnd;
            yEnd = temp;
        }
        if (zStart > zEnd){
            temp = zStart;
            zStart = zEnd;
            zEnd = temp;
        }
        player.sendMessage("x start: " + xStart + " y start: " + yStart + " z start: " + zStart);
        player.sendMessage("x end: " + xEnd + " y end: " + yEnd + " z end: " + zEnd);
        Random rand = new Random();
        player.sendMessage("here6");
        //try {
            for (int x = xStart; x <= xEnd; x++) {
                for (int y = yStart; y <= yEnd; y++) {
                    for (int z = zStart; z <= zEnd; z++) {
                        // player.sendMessage("x: " + x + " y: " + y + " z: " + z);
                        int rand_int = rand.nextInt(100) + 1;
                        if (rand_int <= percent.get(0)){
                            player.getWorld().getBlockAt(x, y, z).setType(Objects.requireNonNull(Material.matchMaterial(blocks[0].toUpperCase())));
                        }
                        for (int i = 0; i < percent.size() - 1; i++){
                            if (rand_int >= percent.get(i) && rand_int <= percent.get(i+1)){
                                player.getWorld().getBlockAt(x, y, z).setType(Objects.requireNonNull(Material.matchMaterial(blocks[i+1].toUpperCase())));
                            }
                        }
//                        if (rand_int >= percent.get(percent.size() - 1)){
//                            player.getWorld().getBlockAt(x, y, z).setType(Objects.requireNonNull(Material.matchMaterial(blocks[percent.size() - 1].toUpperCase())));
//                            times2 += 1;
//                            block2 = blocks[percent.size() - 1];
//                        }

                    }
                }
            }
//        }catch (Exception e){
//            player.sendMessage(String.valueOf(e));
//        }
        player.sendMessage("here7");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        // sets variables
        Player player = (Player) sender;
        ArrayList<String> blocks = new ArrayList<String>();

        // check command
        if (cmd.getName().equalsIgnoreCase("setcell") || cmd.getName().equalsIgnoreCase("sc")){

            // sets variables
            int len = args.length;
            if (len == 0 || len >= 3){
                return null;
            }
            if (len == 1){
                PrisonBlocksManager prisonBlocksManager = new PrisonBlocksManager(plugin);
                ArrayList<String> cells = prisonBlocksManager.getAllBlocks();
                return cells;
            }
            String arg = args[1];
            String[] newArg = arg.split(",");
            arg = newArg[newArg.length-1];
//            if (arg.endsWith(",")){
//                arg = "";
//            }
            Material[] allItems = Material.values();
            //String[] allOres = {"STONE","COAL_ORE","IRON_ORE","GOLD_ORE","DIAMOND_ORE","EMERALD_ORE","COAL_BLOCK","IRON_BLOCK","GOLD_BLOCK","DIAMOND_BLOCK","EMERALD_BLOCK"};
            String[] allOres = config.getConfigurationSection("BLOCK_WORTH").getKeys(true).toArray(new String[0]);

            if (arg.equalsIgnoreCase("")) {
                for ( String val : allOres) {

                    //Material itemName = val;
                    blocks.add(String.valueOf(val));

                }

            }else {
                for ( String val : allOres) {

                    //Material itemName = val;
                    if (String.valueOf(val).startsWith(arg.toUpperCase())){
                        blocks.add(String.valueOf(val));
                    }

                }

            }
            return blocks;
        }
        return null;
    }
}
