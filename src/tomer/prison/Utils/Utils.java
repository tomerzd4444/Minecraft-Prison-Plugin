package tomer.prison.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;
import tomer.prison.PrisonPlugin;
import tomer.prison.User.PlayerYAMLUtil;
import tomer.prison.managers.BalanceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Utils {
    private static PrisonPlugin plugin;
    public static HashMap<String, Enchantment> enchantments = new HashMap<String, Enchantment>();

    static {
        enchantments.put("alldamage", Enchantment.DAMAGE_ALL);
        enchantments.put("sharpness", Enchantment.DAMAGE_ALL);
        enchantments.put("ardmg", Enchantment.DAMAGE_ARTHROPODS);
        enchantments.put("baneofarthropods", Enchantment.DAMAGE_ARTHROPODS);
        enchantments.put("undeaddamage", Enchantment.DAMAGE_UNDEAD);
        enchantments.put("smite", Enchantment.DAMAGE_UNDEAD);
        enchantments.put("digspeed", Enchantment.DIG_SPEED);
        enchantments.put("efficiency", Enchantment.DIG_SPEED);
        enchantments.put("durability", Enchantment.DURABILITY);
        enchantments.put("unbreaking", Enchantment.DURABILITY);
        enchantments.put("thorns", Enchantment.THORNS);
        enchantments.put("highcrit", Enchantment.THORNS);
        enchantments.put("fireaspect", Enchantment.FIRE_ASPECT);
        enchantments.put("fire", Enchantment.FIRE_ASPECT);
        enchantments.put("knockback", Enchantment.KNOCKBACK);
        enchantments.put("fortune", Enchantment.LOOT_BONUS_BLOCKS);
        enchantments.put("mobloot", Enchantment.LOOT_BONUS_MOBS);
        enchantments.put("looting", Enchantment.LOOT_BONUS_MOBS);
        enchantments.put("respiration", Enchantment.OXYGEN);
        enchantments.put("protection", Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantments.put("protect", Enchantment.PROTECTION_ENVIRONMENTAL);
        enchantments.put("blastprotect", Enchantment.PROTECTION_EXPLOSIONS);
        enchantments.put("featherfall", Enchantment.PROTECTION_FALL);
        enchantments.put("fireprotection", Enchantment.PROTECTION_FIRE);
        enchantments.put("fireprotect", Enchantment.PROTECTION_FIRE);
        enchantments.put("fireprot", Enchantment.PROTECTION_FIRE);
        enchantments.put("projectileprotection", Enchantment.PROTECTION_PROJECTILE);
        enchantments.put("silktouch", Enchantment.SILK_TOUCH);
        enchantments.put("aquaaffinity", Enchantment.WATER_WORKER);
        enchantments.put("flame", Enchantment.ARROW_FIRE);
        enchantments.put("power", Enchantment.ARROW_DAMAGE);
        enchantments.put("infinity", Enchantment.ARROW_INFINITE);
        enchantments.put("luck", Enchantment.LUCK);
        enchantments.put("lure", Enchantment.LURE);
    }

    public Utils(PrisonPlugin plugin) {
        Utils.plugin = plugin;
    }

    public static String chat(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static ItemStack createItem(Inventory inv, Material material, int amount, int invSlot, String displayName, String... loreString) {
        ItemStack item;
        List<String> lore = new ArrayList();

        item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        for (String s: loreString){
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack createStackItem(Inventory inv, ItemStack item){
        ItemMeta meta = item.getItemMeta();
        String displayName = meta.getDisplayName();
        List<String> lore = meta.getLore();
        int amount = item.getAmount();
        int invSlot = 0;
        ItemStack[] items = inv.getContents();
        for (int i = 0; i < items.length; i ++){
            ItemStack itemCheck = items[i];
            if (itemCheck == item){
                invSlot = i;
                break;
            }
        }
        meta.setDisplayName(Utils.chat(displayName));
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }

    public static ItemStack createItemByte(Inventory inv, String materialString, int byteId, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList();

        item = new ItemStack(Material.matchMaterial(materialString), amount, (short) byteId);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utils.chat(displayName));
        for (String s: loreString){
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }
    public static void setBalScoreboard(Player player) {
        BalanceManager balanceManager = new BalanceManager(plugin, PrisonPlugin.path);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("INFO");
        int money = balanceManager.getPlayerCurrency(player);
        Score bal = objective.getScore(Utils.chat("&3Balance: ") + Utils.chat("&f") + money + PrisonPlugin.config.getString("BALANCE.SIGN"));
        bal.setScore(2);
        Score warp = objective.getScore(Utils.chat("&4Rank: " + "&5" + PlayerYAMLUtil.readFile(player).getRank()));
        warp.setScore(1);
        player.setScoreboard(board);
    }
    public static String[] stringToArrayList(String str){
        String[] toReturn;
        toReturn = str.split(",");
        return toReturn;
    }
    public static void sell(Player player, int invSlot) {
        if (invSlot == -1) {
            player.sendMessage("A problem has occurred");
            return;
        }
        FileConfiguration config = plugin.getConfig();
        Inventory inv = player.getInventory();
        ItemStack item = inv.getItem(invSlot);
        if (item == null) {
            return;
        }
        String name = item.getType().toString();
        int amount = item.getAmount();
        BalanceManager balanceManager = new BalanceManager(plugin, PrisonPlugin.path);
        if (config.getString("BLOCK_WORTH." + name.toUpperCase()) != null) {
            balanceManager.addCurrencyToPlayer(player, config.getInt("BLOCK_WORTH." + name.toUpperCase()) * amount);
        } else {
            return;
        }
        inv.setItem(invSlot, new ItemStack(Material.AIR));
    }

    public static String convertToEnch(String enchName) {
        return enchantments.get(enchName.toLowerCase()).getName();
    }

    public static ArrayList<String> getEnchantments() {
        return new ArrayList<String>(enchantments.keySet());
    }

    public static Player getPlayerByUuid(UUID uuid) {
        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
            if (!(p instanceof Player)) {
                continue;
            }
            if (p.getUniqueId().equals(uuid)) {
                return (Player) p;
            }
        }
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getUniqueId().equals(uuid)) {
                return p;
            }
        }
        return null;
    }
}
