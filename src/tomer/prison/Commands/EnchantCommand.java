package tomer.prison.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomer.prison.PrisonPlugin;

public class EnchantCommand implements CommandExecutor {
    private PrisonPlugin plugin;

    public EnchantCommand(PrisonPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("enchant").setExecutor(this::onCommand);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length != 2){
            player.sendMessage("Should be 2 arguments!");
            return false;
        }
        ItemStack item = player.getItemInHand();
        String enchName = args[0].toUpperCase();
        String name = item.getType().toString();
        if (enchName.equalsIgnoreCase("fortune")){
            // 	LOOT_BONUS_BLOCKS
            //  LOOT_BONUS_MOBS
            if (name.contains("SWORD")){
                enchName = "LOOT_BONUS_MOBS";
            }else if (name.contains("PICKAXE")){
                enchName = "LOOT_BONUS_BLOCKS";
            }
        }
        int level = Integer.parseInt(args[1]);
        if (level < 1){
            player.sendMessage("level is too low!");
            return false;
        }else if (level > 9999){
            player.sendMessage("level is too high!");
            return false;
        }
        Enchantment ench = Enchantment.getByName(enchName);
        item.addUnsafeEnchantment(ench,level);
        return false;
    }
}
