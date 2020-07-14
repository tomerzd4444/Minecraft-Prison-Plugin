package tomer.prison.managers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;
import tomer.prison.Utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BalanceManager {
    private final HashMap<String,Integer> currency = new HashMap<String,Integer>();
    public PrisonPlugin plugin;
    private final String fileName;// = "D:/javaProjects/minecraftPlugins/PrisonPlugin/src/tomer/prison/managers/Data/balance.txt";

    public BalanceManager(PrisonPlugin plugin, String path) {
        this.plugin = plugin;
        fileName = path + "/balance.txt";
    }

    public void saveCurrencyFile() throws IOException {
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            FileWriter myWriter = null;
            try {
                File writer = new File(fileName);
                List<String> lines = FileUtils.readLines(writer);
                FileUtils.writeLines(writer, lines, false);
//                myWriter = new FileWriter(fileName);
//                myWriter.write(p.getUniqueId() + "," + currency.get(p.getUniqueId()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void loadCurrencyFile() throws IOException, ClassNotFoundException {
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String data = "";
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                String id = "";
                String[] parts = data.split(",");
                if (parts.length == 2) {
                    id = parts[0];
                    int r = Integer.parseInt(parts[1]);
                    if (!id.equals("")) {
                        currency.put(id, r);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void removeAndChangeBalance(Player player, int fAmount) throws IOException {
//        ScoreboardManager manager = Bukkit.getScoreboardManager();
//        Scoreboard board = manager.getNewScoreboard();
//        Objective objective = board.registerNewObjective("test", "dummy");
//        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
//        objective.setDisplayName("INFO");
//        Score score = objective.getScore(Utils.chat("&3Balance:")); //Get a fake offline player
//        score.setScore(getPlayerCurrency(player));
//        player.setScoreboard(board);
        List<String> lines = FileUtils.readLines(new File(fileName));
        // FileWriter myWriter = new File(fileName);
        List<String> updatedLines = lines.stream().filter(s -> !s.contains(player.getName() + ",")).collect(Collectors.toList());
        player.sendMessage("updated lines: " + Arrays.toString(new List[]{updatedLines}));
        FileUtils.writeLines(new File(fileName), updatedLines, false);
        Collection<String> linesToWrite = new ArrayList<String>();
        //linesToWrite.add(System.getProperty( "line.separator" ));
        linesToWrite.add(player.getName() + "," + fAmount);
//        myWriter.write(System.getProperty( "line.separator" ));
//        myWriter.write(player.getName() + "," + fAmount);
        FileUtils.writeLines(new File(fileName), linesToWrite, true);
        //myWriter.close();
        currency.put(player.getName(), fAmount);
        Utils.setBalScoreboard(player);
    }
    public void addCurrencyToPlayer(OfflinePlayer p, int amount){
        FileWriter myWriter = null;
        Player player = Bukkit.getPlayer(p.getName());
        int bAmount = getPlayerCurrency(p);
        int fAmount = bAmount + amount;
        try {
//            myWriter = new FileWriter(fileName);
//            player.sendMessage(myWriter.toString());
//            myWriter.write(p.getUniqueId() + "," + fAmount);
//            myWriter.close();
//            player.sendMessage(p.getUniqueId() + "," + fAmount);
//            myWriter = new FileWriter(fileName);
//            List<String> lines = FileUtils.readLines(new File(fileName));
//            List<String> updatedLines = lines.stream().filter(s -> !s.contains(player.getName())).collect(Collectors.toList());
//            FileUtils.writeLines(new File(fileName), updatedLines, false);
//            myWriter.write(System.getProperty( "line.separator" ));
//            myWriter.write(player.getName() + "," + fAmount);
//            myWriter.close();
            removeAndChangeBalance(player,fAmount);

        } catch (IOException e) {
            player.sendMessage(e.toString());
        }
    }
    public void removeCurrencyFromPlayer(OfflinePlayer p, int amount){
        FileWriter myWriter = null;
        Player player = Bukkit.getPlayer(p.getName());
        int bAmount = getPlayerCurrency(p);
        int fAmount = bAmount - amount;
        try {
//            myWriter = new FileWriter(fileName);
//            player.sendMessage(myWriter.toString());
//            myWriter.write(p.getUniqueId() + "," + fAmount);
//            myWriter.close();
//            player.sendMessage(p.getUniqueId() + "," + fAmount);
//            myWriter = new FileWriter(fileName);
//            List<String> lines = FileUtils.readLines(new File(fileName));
//            List<String> updatedLines = lines.stream().filter(s -> !s.contains(player.getName())).collect(Collectors.toList());
//            FileUtils.writeLines(new File(fileName), updatedLines, false);
//            myWriter.write(System.getProperty( "line.separator" ));
//            myWriter.write(player.getName() + "," + fAmount);
//            myWriter.close();
            removeAndChangeBalance(player,fAmount);
        } catch (IOException e) {
            player.sendMessage(e.toString());
        }
    }
    public void setPlayerCurrency(OfflinePlayer p, int amount){
        //currency.put(p.getUniqueId().toString(), amount);
        FileWriter myWriter = null;
        Player player = Bukkit.getPlayer(p.getName());
        try {
//            myWriter = new FileWriter(fileName);
//            player.sendMessage(myWriter.toString());
//            myWriter.write(p.getUniqueId() + "," + amount);
//            myWriter.close();
//            player.sendMessage(p.getUniqueId() + "," + amount);
//            myWriter = new FileWriter(fileName);
//            List<String> lines = FileUtils.readLines(new File(fileName));
//            List<String> updatedLines = lines.stream().filter(s -> !s.contains(player.getName())).collect(Collectors.toList());
//            FileUtils.writeLines(new File(fileName), updatedLines, false);
//            myWriter.write(System.getProperty( "line.separator" ));
//            myWriter.write(player.getName() + "," + amount);
//            myWriter.close();
            removeAndChangeBalance(player,amount);

        } catch (Exception e) {
            player.sendMessage(e.toString());
        }
    }
    public int getPlayerCurrency(OfflinePlayer p) {
        int ra = 0;
        Player player = Bukkit.getPlayer(Objects.requireNonNull(p.getName()));
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            String data = "";
            int loc = 0;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                String[] parts = data.split(",");
                if (parts.length == 2) {
                    String id = parts[0];
                    ra = Integer.parseInt(parts[1]);
                    if (id.equals(p.getUniqueId().toString())) {
                        break;
                    }
                }
            }
            // ra = data.substring(loc);
            myReader.close();
        } catch (FileNotFoundException e) {
            player.sendMessage("An error occurred.");
            e.printStackTrace();
        }
        return ra;
    }
}
