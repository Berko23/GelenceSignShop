package me.berko.gelencesignshop.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TransactionManager {

    /**
     * Remove the given value from player's balance (if it has enough) and give it the itemStack.
     * @param player the target player
     * @param itemStack the item(s) to give to the target player
     * @param value the amount of money to take from the target player
     * @return true if the transaction was completed successfully, false otherwise.
     */
    public static boolean buyItem(Player player, ItemStack itemStack, double value) {
        //TODO: buy item logic (params are not sure)
        return false;
    }

    /**
     * Remove the itemStack from player (if it has it) and add the value to its balance.
     * @param player the target player
     * @param itemStack the item(s) to take from the target player
     * @param value the amount of money to give to the target player
     * @return true if the transaction was completed successfully, false otherwise.
     */
    public static boolean sellItem(Player player, ItemStack itemStack, double value) {
        //TODO: sell item logic (params are not sure)
        return false;
    }
}
