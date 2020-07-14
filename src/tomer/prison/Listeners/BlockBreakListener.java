package tomer.prison.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.BalanceManager;

import java.util.Collection;

public class BlockBreakListener implements Listener {
    private final PrisonPlugin plugin;
    private final FileConfiguration config;
    private final BalanceManager balanceManager;

    public BlockBreakListener(PrisonPlugin plugin) {
        this.plugin = plugin;
        this.balanceManager = new BalanceManager(plugin, PrisonPlugin.path);
        config = plugin.getConfig();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block =  event.getBlock();
        Material mat = block.getBlockData().getMaterial();
        ItemStack pickaxe = player.getItemInHand();
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        String name = pickaxe.getType().toString();
//        if (name.equalsIgnoreCase("diamond_pickaxe")){
//            player.sendMessage(Arrays.toString(new Map[]{pickaxeMeta.getEnchants()}));
//            ArrayList<Enchantment> enchantments = new ArrayList<Enchantment>(pickaxeMeta.getEnchants().keySet());
//            for (Enchantment i : enchantments){
//                player.sendMessage(String.valueOf(i));
//                String enchName = i.getName();
//                player.sendMessage(enchName + " level: " + pickaxeMeta.getEnchantLevel(i));
//            }
//        }
        Collection<ItemStack> drops = block.getDrops(pickaxe);
        block.getDrops(pickaxe).clear();
        for(ItemStack i : drops) {
            event.getBlock().getDrops(pickaxe).remove(i);
            player.getInventory().addItem(i);
        }
        event.setCancelled(true);
        block.setType(Material.AIR);
//        for (String key : config.getConfigurationSection("BLOCK_WORTH").getKeys(true)) {
//            String string = config.getString("BLOCK_WORTH." + key);
//            if (mat.toString().equals(key)){
//                balanceManager.addCurrencyToPlayer(player, Integer.parseInt(string));
//            }
//        }
    }
}
