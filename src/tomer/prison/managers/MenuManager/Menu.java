package tomer.prison.managers.MenuManager;

import tomer.prison.PrisonPlugin;

import java.io.FileWriter;
import java.io.IOException;

public class Menu {
    //    public PrisonPlugin plugin;
//    public Menu(PrisonPlugin plugin){
//        this.plugin = plugin;
//    }
    protected static PrisonPlugin plugin;
    public static int invSize = 27;
    public static String fileName;// = "D:/javaProjects/minecraftPlugins/PrisonPlugin/src/tomer/prison/managers/MenuManager/Utils/menu.txt";
    protected FileWriter myWriter;

//    {
//        try {
//            myWriter = new FileWriter(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void initialize(PrisonPlugin prisonPlugin, String path) throws IOException {
        plugin = prisonPlugin;
        //SetMenuSize setMenuSize = new SetMenuSize();
        fileName = path + "/menu.txt";
        myWriter = new FileWriter(fileName);
        SetMenuSize.setup();
    }

}
