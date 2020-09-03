package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tomer.prison.PrisonPlugin;

public class FlyCommand implements CommandExecutor {
    PrisonPlugin plugin;

    public FlyCommand(PrisonPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("fly").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.setAllowFlight(!player.getAllowFlight());
        player.setFlying(!player.isFlying());
        return false;
    }
}
