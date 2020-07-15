package tomer.prison.User;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerYAMLUtil {
    private static PrisonPlugin plugin;
    private static String path;

    public PlayerYAMLUtil(PrisonPlugin plugin, String path) {
        PlayerYAMLUtil.plugin = plugin;
        PlayerYAMLUtil.path = path;
    }

    public static void createPlayerFile(Player player) {
        String name = player.getName();
        player.sendMessage(name);
        UUID id = player.getUniqueId();
        File file = new File(path + "/Players/" + id.toString() + ".yml");
        boolean result;
        try {
            result = file.createNewFile();  //creates a new file
            if (result) {
                player.sendMessage("file created " + file.getCanonicalPath()); //returns the path string
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("balance: " + PrisonPlugin.config.getString("BALANCE.START"));
        lines.add("rank: " + PrisonPlugin.config.getString("RANK.DEFAULT"));
        try {
            FileUtils.writeLines(file, lines, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(Player player) {
        String name = player.getName();
        player.sendMessage(name);
        UUID id = player.getUniqueId();
        player.sendMessage(id.toString());
        player.sendMessage(path);
        File file = new File(path + "/Players/" + id.toString() + ".yml");
        return file.exists();
    }

    public static UserData readFile(Player player) {
        UUID id = player.getUniqueId();
        String pathname = path + "/Players/" + id.toString() + ".yml";
        UserData userData = new UserData(PlayerYAMLUtil.path);
        File file = new File(pathname);
        FileConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        userData.setBalance(yaml.getInt("balance"), id);
        userData.setRank(yaml.getString("rank"), id);
        return userData;
    }

//    public static void setRank(Player player, String rank){
//        UUID id = player.getUniqueId();
//        String pathname = path + "/Players/" + id.toString() + ".yml";
//        player.sendMessage("pathname: " + pathname);
//        System.out.println("pathname: " + pathname);
//        UserData userData = new UserData(path);
//        userData.setRank(rank, id);
//        player.sendMessage("bal: " + userData.getBalance() + " rank: " + userData.getRank());
//    }
//
//    public static void setBalance(Player player, int balance){
//        UUID id = player.getUniqueId();
//        String pathname = path + "/Players/" + id.toString() + ".yml";
//        player.sendMessage("pathname: " + pathname);
//        System.out.println("pathname: " + pathname);
//        UserData userData = new UserData(path);
//        userData.setBalance(balance, id);
//        player.sendMessage("bal: " + userData.getBalance() + " rank: " + userData.getRank());
//    }
}
