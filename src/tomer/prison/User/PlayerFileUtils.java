package tomer.prison.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerFileUtils {
    private static PrisonPlugin plugin;
    private static String path;

    public PlayerFileUtils(PrisonPlugin plugin, String path) {
        PlayerFileUtils.plugin = plugin;
        PlayerFileUtils.path = path;
    }

    public static void createPlayerFile(Player player) {
        String name = player.getName();
        player.sendMessage(name);
        UUID id = player.getUniqueId();
        player.sendMessage(id.toString());
        player.sendMessage(path);
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
        lines.add("balance: 0");
        lines.add("rank: a");
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
        player.sendMessage("pathname: " + pathname);
        System.out.println("pathname: " + pathname);
        UserData userData;
        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            userData = mapper.readValue(new File(pathname), UserData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userData;
    }
}
