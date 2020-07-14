package tomer.prison.managers.MenuManager.Utils;

import tomer.prison.managers.MenuManager.Menu;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteToFile {
    public static String fileName = Menu.fileName;
    public static FileWriter myWriterInt;
    public static FileWriter myWriterString;

//    static {
//        try {
//            myWriterString = new FileWriter(fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void writeToFile(Integer size) throws IOException {
        ArrayList<String> lines = new ArrayList<String>();
        int len = ReadFromFile.readLinesFromFile().size();
        System.out.println("len: " + len);
        for (int i = 1; i < len; i++){
            lines.add(ReadFromFile.readLineFromFile(i));
        }
        System.out.println(lines);
        myWriterInt = new FileWriter(fileName);
        myWriterInt.write(size.toString());
        myWriterInt.close();
        FileWriter myWriter = new FileWriter(fileName,true);
        for (String i : lines){
            System.out.println(i);
            myWriter.write(System.getProperty("line.separator"));
            myWriter.write(i);
        }
        myWriter.close();
    }
    public static void writeToFile(String name, int amount, int invSlot, String sellPrice, String buyPrice) throws IOException {
        myWriterString = new FileWriter(fileName,true);
        myWriterString.write(System.getProperty( "line.separator" ));
        myWriterString.write(name + "," + amount + "," + invSlot + "," + sellPrice + "," + buyPrice);
        myWriterString.close();
    }
}
