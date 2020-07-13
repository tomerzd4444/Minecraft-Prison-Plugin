package tomer.prison.managers.MenuManager;

import tomer.prison.PrisonPlugin;

import javax.annotation.Nullable;
import java.io.FileWriter;
import java.io.IOException;

public class Menu {
    //    public PrisonPlugin plugin;
//    public Menu(PrisonPlugin plugin){
//        this.plugin = plugin;
//    }
    protected static PrisonPlugin plugin;
    public static int invSize = 27;
    protected String fileName = "Utils/menu.txt";
    protected FileWriter myWriter;

    {
        try {
            myWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPlugin(PrisonPlugin prisonPlugin) throws IOException {
        plugin = prisonPlugin;
        //SetMenuSize setMenuSize = new SetMenuSize();
        SetMenuSize.setup();
    }

}
