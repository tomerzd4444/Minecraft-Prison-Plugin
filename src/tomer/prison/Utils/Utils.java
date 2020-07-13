package tomer.prison.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.*;
import tomer.prison.PrisonPlugin;
import tomer.prison.managers.BalanceManager;
import tomer.prison.managers.MenuManager.Utils.ReadFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {
    private static PrisonPlugin plugin;

    public Utils(PrisonPlugin plugin){
        Utils.plugin = plugin;
    }
    public static String chat (String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static ItemStack createItem(Inventory inv, Material material, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList();

        item = new ItemStack(material, amount);//Material.matchMaterial(materialString), amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        for (String s: loreString){
            lore.add(Utils.chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        // inv.setItem(invSlot, item);
        return item;
    }
    public static ItemStack createStackItem(Inventory inv, ItemStack item){
        //ItemStack item;

        //item = new ItemStack(material, amount);//Material.matchMaterial(materialString), amount);

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
//    public static void setRank(String p, String pRank){
//        Player player = Bukkit.getPlayer(p);
//        player.sendMessage("Changing rank");
//        try {
//            player.sendMessage("Changed your rank from " + rankingManager.getPlayerRank(Bukkit.getOfflinePlayer(p)) + " to " + pRank);
//            rankingManager.changePlayerRank(Bukkit.getOfflinePlayer(p), pRank);
//            PermissionAttachment attachment = player.addAttachment(plugin);
//            String[] rank = {};
//            String[] higherRank = {};
//            player.sendMessage(pRank + " " + pRank.charAt(0) + pRank.charAt(1));
//            String r = pRank.substring(2);
//            if (r.equalsIgnoreCase("Owner") || r.equalsIgnoreCase("Admin") || r.equalsIgnoreCase("Moderator") || r.equalsIgnoreCase("Normie")){
//                pRank = pRank.substring(2);
//            }
//
////            if (pRank.startsWith("r")){
////                pRank = pRank.substring(2);
////            }
//            player.sendMessage(pRank);
//            //String pRank = rankingManager.getPlayerRank(player);
//            if (pRank.equalsIgnoreCase("Normie")){
//                rank = normieRank;
//                higherRank = modRank;
//                player.setDisplayName(Utils.chat("&7[&5Normie&7] " + player.getName()));
//                plugin.addToTeam(player,1);
//            } else if (pRank.equalsIgnoreCase("Moderator")){
//                rank = modRank;
//                higherRank = adminRank;
//                player.setDisplayName(Utils.chat("&7[&3Moderator&7] " + player.getName()));
//                plugin.addToTeam(player,2);
//            } else if (pRank.equalsIgnoreCase("Admin")){
//                rank = adminRank;
//                higherRank = new String[]{};
//                player.setDisplayName(Utils.chat("&7[&5Admin&7] " + player.getName()));
//                plugin.addToTeam(player,3);
//            }else if (pRank.equalsIgnoreCase("Owner")){
//                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),"op " + player.getName());
//                rank = new String[]{};
//                higherRank = new String[]{};
//                player.setDisplayName(Utils.chat("&7[&4OWNER&7] " + player.getName()));
//                plugin.addToTeam(player,4);
//            }
//            player.sendMessage(String.format("%s\n%s", Arrays.toString(rank), Arrays.toString(higherRank)));
//            for (String i : higherRank){
//                attachment.setPermission(i,false);
//            }
//            for (String i : rank){
//                attachment.setPermission(i,true);
//            }
//        } catch (Exception e){
//            player.sendMessage(e.toString());
//        }
//    }
    public static void setBalScoreboard(Player player){
        BalanceManager balanceManager = new BalanceManager(plugin);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("INFO");
        Score bal = objective.getScore(Utils.chat("&3Balance:"));
        bal.setScore(balanceManager.getPlayerCurrency(player));
        Score rank = objective.getScore(Utils.chat("&4Rank: " + "&6" + "Normie"));
        rank.setScore(1);
        Score warp = objective.getScore(Utils.chat("&2Warp: " + "&5" + "A"));
        warp.setScore(0);
        player.setScoreboard(board);
    }
    public static String[] stringToArrayList(String str){
        String[] toReturn;
        toReturn = str.split(",");
        return toReturn;
    }
    public static void sell(Player player, int invSlot){
        if (invSlot == -1){
            player.sendMessage("A problem has occurred");
            return;
        }
        FileConfiguration config = plugin.getConfig();
        Inventory inv = player.getInventory();
        ItemStack item = inv.getItem(invSlot);
        player.sendMessage(String.valueOf(invSlot));
        player.sendMessage(String.valueOf(item));
        String name = item.getType().toString();
        int amount = item.getAmount();
        player.sendMessage(String.valueOf(amount));
        player.sendMessage(name);
        BalanceManager balanceManager = new BalanceManager(plugin);
        for (String key : config.getConfigurationSection("BLOCK_WORTH").getKeys(true)) {
            String string = config.getString("BLOCK_WORTH." + key);
            if (string == null){
                return;
            }
            if (name.equals(key)){
                player.sendMessage("key: " + key);
                player.sendMessage("name: " + name);
                balanceManager.addCurrencyToPlayer(player, Integer.parseInt(string)*amount);
            }
        }
    }
}
