package berko23.gelencesignshop.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TransactionManager {

    /**
     * Remove the given value from player's balance (if it has enough) and give it the itemStack.
     * @param player
     * @param itemStack
     * @return true if the transaction was completed successfully, false otherwise.
     */
    public static boolean buyItemFromPlayer(Player player, ItemStack itemStack, double value) {
        //TODO: buy item logic (params are not sure)
        return false;
    }

    /**
     * Remove the itemStack from player (if it has it) and add the value to its balance.
     * @param player
     * @param itemStack
     * @return true if the transaction was completed successfully, false otherwise.
     */
    public static boolean sellItemToPlayer(Player player, ItemStack itemStack) {
        //TODO: sell item logic (params are not sure)
        return false;
    }
}
