package tomer.prison.managers.MenuManager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class SetMenuSize extends Menu {
    private static SetMenuSize menuSize;

    public static void setup() {
        menuSize = new SetMenuSize();
    }

    public static void setSize(int size, @Nullable Player player) {
        invSize = size;
//        WriteToFile.writeToFile(size);
        FileConfiguration config = Menu.plugin.getConfig();
        config.set("MENU.SIZE", invSize);
        if (player != null) {
            player.sendMessage("Changed the inventory's size to " + size);
        } else {
            System.out.println("Changed the inventory's size to " + size);
        }
//        if (ReadFromFile.readLineFromFile(0) == null){
//            setSize(size,player);
//        }
    }
}
