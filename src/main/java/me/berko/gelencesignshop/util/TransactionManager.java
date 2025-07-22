package me.berko.gelencesignshop.util;

import me.berko.gelencesignshop.economy.EconomyHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class TransactionManager {
    private static final Economy econ = EconomyHandler.getEconomy();

    /**
     * Remove the given value from player's balance (if it has enough) and give it the itemStack.
     * @param player the target player
     * @param itemStack the item(s) to give to the target player
     * @param value the amount of money to take from the target player
     */
    public static boolean buyItem(Player player, ItemStack itemStack, double value) {
        if (!econ.has(player, value)) {
            player.sendMessage(ChatColor.RED + "You don't have enough money! (you need " +
                    ChatColor.YELLOW + PriceParser.formatPrice(value - econ.getBalance(player)) + " GV" +
                    ChatColor.RED +")");
            return false;
        }

        // Check if there is space for the FULL item stack
        ItemStack cloned = itemStack.clone();
        int amountToAdd = cloned.getAmount();

        int space = getFreeSpaceForItem(player, cloned);
        System.out.println("Free space: " + space); //DEBUG
        if (space < amountToAdd) {
            player.sendMessage(ChatColor.RED + "Not enough inventory space for this purchase.");
            return false;
        }

        // if all ok -> complete transaction
        econ.withdrawPlayer(player, value);
        player.getInventory().addItem(cloned);
        player.sendMessage(ChatColor.GREEN + "You bought " +
                ChatColor.YELLOW + cloned.getAmount() + "×" + cloned.getType() +
                ChatColor.GREEN + " for " +
                ChatColor.YELLOW + PriceParser.formatPrice(value) + " GV.");
        return true;
    }

    /**
     * Remove the itemStack from player (if it has it) and add the value to its balance.
     * @param player the target player
     * @param itemStack the item(s) to take from the target player
     * @param value the amount of money to give to the target player
     */
    public static boolean sellItem(Player player, ItemStack itemStack, double value) {
        int requiredAmount = itemStack.getAmount();
        ItemStack checkItem = itemStack.clone();
        checkItem.setAmount(1); // we don't need the amount for checking

        int found = countSimilarItems(player, checkItem);
        if (found < requiredAmount) {
            player.sendMessage(ChatColor.RED + "You don't have enough items to sell.");
            return false;
        }

        boolean removed = removeItems(player, checkItem, requiredAmount);
        if (!removed) {
            player.sendMessage(ChatColor.RED + "Failed to remove items from inventory.");
            return false;
        }

        EconomyHandler.deposit(player, value);
        player.sendMessage(ChatColor.GREEN + "You sold " +
                ChatColor.YELLOW + requiredAmount + "×" + itemStack.getType() +
                ChatColor.GREEN + " for " +
                ChatColor.YELLOW + PriceParser.formatPrice(value) + " GV.");
        return true;
    }

    private static int getFreeSpaceForItem(Player player, ItemStack item) {
        int free = 0;
        int maxStack = item.getMaxStackSize();


        for (ItemStack invItem : player.getInventory().getStorageContents()) {
            if (invItem == null || invItem.getType().isAir()) {
                free += maxStack;
            } else if (invItem.isSimilar(item)) {
                free += maxStack - invItem.getAmount();
            }
        }

        return free;
    }

    private static int countSimilarItems(Player player, ItemStack target) {
        int count = 0;
        for (ItemStack item : player.getInventory().getStorageContents()) {
            if (item != null && item.isSimilar(target)) {
                count += item.getAmount();
            }
        }
        return count;
    }

    private static boolean removeItems(Player player, ItemStack target, int amount) {
        ItemStack[] contents = player.getInventory().getStorageContents();
        int toRemove = amount;

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null || !item.isSimilar(target)) continue;

            int stackAmount = item.getAmount();
            if (stackAmount <= toRemove) {
                toRemove -= stackAmount;
                contents[i] = null;
            } else {
                item.setAmount(stackAmount - toRemove);
                toRemove = 0;
            }

            if (toRemove == 0) break;
        }

        if (toRemove > 0) return false;

        player.getInventory().setStorageContents(contents);
        return true;
    }
}
