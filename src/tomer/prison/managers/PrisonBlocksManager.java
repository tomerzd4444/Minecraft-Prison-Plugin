package tomer.prison.managers;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class PrisonBlocksManager {
    public PrisonPlugin plugin;
    private final String fileName = "blocksPos.txt";

    public PrisonBlocksManager(PrisonPlugin plugin) {
        this.plugin = plugin;
    }

    public void setBlock(String name, String x1, String y1, String z1, String x2, String y2, String z2, Player player){

        // sets writer
        FileWriter myWriter = null;
        try {
            // sets variables
            myWriter = new FileWriter(fileName, true);
            String str = name + "," + x1 + "," + y1 + "," + z1 + "," + x2 + "," + y2 + "," + z2;

            // writes to file
            myWriter.write(System.getProperty( "line.separator" ));
            myWriter.write(str);
            myWriter.close();

            // debug
            player.sendMessage(str);

        } catch (IOException e) {
            // debug
            player.sendMessage(e.toString());
        }
    }
    public ArrayList<String> getAllBlocks(){
        ArrayList<String> cells = new ArrayList<String>();
        try {
            // sets variables
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String data = "";
            int loc = 0;

            // loops over file
            while (myReader.hasNextLine()) {

                // gets line and checks if it matches the name
                data = myReader.nextLine();
                String id = "";
                String[] parts = data.split(",");
                id = parts[0];
                cells.add(id);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // debug
            e.printStackTrace();
        }
        return cells;
    }
    public ArrayList<String> getBlock(String name, Player player) {
        ArrayList<String> locations = null;
        try {
            // sets variables
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String data = "";
            int loc = 0;

            // loops over file
            while (myReader.hasNextLine()) {

                // gets line and checks if it matches the name
                data = myReader.nextLine();
                String id = "";
                player.sendMessage(data);
                String[] parts = data.split(",");
                id = parts[0];
                if (id.equals(name)){
                    locations = new ArrayList<String>();
                    Collections.addAll(locations, Arrays.copyOfRange(parts, 1, parts.length));
                    break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            // debug
            player.sendMessage("An error occurred: " + e);
            e.printStackTrace();
        }
        return locations;
    }
    public void removeBlock(String name, Player player) {
        FileWriter myWriter = null;
        try {
            // removes the block
            List<String> lines = FileUtils.readLines(new File(fileName));
            List<String> updatedLines = lines.stream().filter(s -> !s.contains(name)).collect(Collectors.toList());
            FileUtils.writeLines(new File(fileName), updatedLines, false);


        } catch (IOException e) {
            // debug
            player.sendMessage(e.toString());
        }
    }
}
