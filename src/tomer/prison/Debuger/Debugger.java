package tomer.prison.Debuger;

import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class Debugger {
    public static Player getOwner() {
        return getServer().getPlayer("blueblood4444");
    }

    public static void sendOwnerMessage(String message) {
        if (getOwner() == null) {
            return;
        }
        getOwner().sendMessage(message);
    }
}
