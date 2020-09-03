package tomer.prison.User;

import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import tomer.prison.PrisonPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserData {
    private int balance;
    private String rank;
    private final String path;

    public UserData(String path) {
        this.path = path;
    }

    private void set(String varPath, String value, UUID id) {
        String fileName = PrisonPlugin.path + File.separator + "playerdata" + File.separator + id.toString() + ".yml";//System.getProperty("user.dir") + "/plugins/PrisonPlugin/playerdata/" + id + ".yml";
        List<String> lines = null;
        try {
            lines = FileUtils.readLines(new File(fileName));
            Collection<String> linesToWrite = lines.stream().filter(s -> !s.contains(varPath + ":")).collect(Collectors.toList());
            linesToWrite.add(varPath + ": " + value);
            FileUtils.writeLines(new File(fileName), linesToWrite, false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int b, UUID id) {
        balance = b;
        set("balance", String.valueOf(b), id);
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String r, UUID id) {
        rank = r;
        set("rank", r, id);
    }
}
