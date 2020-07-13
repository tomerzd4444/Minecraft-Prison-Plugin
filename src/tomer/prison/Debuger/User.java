package tomer.prison.Debuger;

import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class User {
    public static Player getOwner() {
        return getServer().getPlayer("blueblood4444");
    }

    public static void sendOwnerMessage(String message) {
        getOwner().sendMessage(message);
    }
}
