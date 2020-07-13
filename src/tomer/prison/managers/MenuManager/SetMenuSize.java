package tomer.prison.managers.MenuManager;

import org.bukkit.configuration.file.FileConfiguration;
import tomer.prison.managers.MenuManager.Utils.ReadFromFile;
import tomer.prison.managers.MenuManager.Utils.WriteToFile;
import org.bukkit.entity.Player;
import javax.annotation.Nullable;
import java.io.IOException;

public class SetMenuSize extends Menu{
    private static SetMenuSize menuSize;
    public static void setup(){
        menuSize = new SetMenuSize();
    }
    public static void setSize(int size,@Nullable Player player) throws IOException {
        System.out.println(menuSize.invSize+" " + size);
        menuSize.invSize = size;
        System.out.println(menuSize.invSize+" " + size);
//        WriteToFile.writeToFile(size);
        FileConfiguration config = Menu.plugin.getConfig();
        config.set("MENU.SIZE", invSize);
        if (player != null){
            player.sendMessage("Changed the inventory's size to " + size);
        }else{
            System.out.println("Changed the inventory's size to " + size);
        }
//        if (ReadFromFile.readLineFromFile(0) == null){
//            setSize(size,player);
//        }
    }
}
