package berko23.gelencesignshop.economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;

public class EconomyHandler {
    private static Economy econ;

    public static void setEconomy(Economy economy) {
        econ = economy;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static boolean has(Player player, double amount) {
        return econ.has(player, amount);
    }

    public static void withdraw(Player player, double amount) {
        econ.withdrawPlayer(player, amount);
    }

    public static void deposit(Player player, double amount) {
        econ.depositPlayer(player, amount);
    }
}

