package tomer.prison.managers.MenuManager.Utils;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ReadFromFile {
    public static String fileName = "menu.txt";
    public static FileWriter myWriter;

    static {
        try {
            myWriter = new FileWriter(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String readLineFromFile(Integer line) throws FileNotFoundException {
        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        String data = "";
        int count = 0;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            if (count == line){
                return data;
            }
            count ++;
        }
        myReader.close();
        return null;
    }
    public static ArrayList<String> readLinesFromFile() throws FileNotFoundException {
        ArrayList<String> toReturn = new ArrayList<String>();
        File myObj = new File(fileName);
        Scanner myReader = new Scanner(myObj);
        String data;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            if (data.length() >= 1) {
                toReturn.add(data);
            }

        }
        myReader.close();
        return toReturn;
    }
}
