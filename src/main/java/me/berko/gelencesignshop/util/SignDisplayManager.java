package me.berko.gelencesignshop.util;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSign;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;

public class SignDisplayManager {
    public static void updateLine1(ShopSign shop) {
        String line1 = formatLine1(shop);

        // update the sign text after a short delay to ensure the block state is fully updated
        Sign sign = (Sign) shop.getLocation().getBlock().getState();
        Bukkit.getScheduler().runTaskLater(GelenceSignShop.getInstance(), () -> {
            sign.getSide(Side.FRONT).setLine(0, line1);
            sign.update();
        }, 4L);
    }

    public static void updateLine4(ShopSign shop) {
        String finalLine = formatLine4(shop);

        // update the sign text after a short delay to ensure the block state is fully updated
        Sign sign = (Sign) shop.getLocation().getBlock().getState();
        Bukkit.getScheduler().runTaskLater(GelenceSignShop.getInstance(), () -> {
            sign.getSide(Side.FRONT).setLine(3, finalLine);
            sign.update();
        }, 4L);
    }

    /**
     * Update the sign text (relevant to shop functions) for the given shop sign. This will not update line 2 or line 3.
     * WARNING: This function requires the sign block to be fully updated and contain the desired text on all lines.
     * (the function updates the whole sign, and overrides the SignChangeEvent update)
     * @param shop The shop sign to update
     */
    public static void updateSign(ShopSign shop) {
        String line1 = formatLine1(shop);
        String line4 = formatLine4(shop);

        Sign sign = (Sign) shop.getLocation().getBlock().getState();
        Bukkit.getScheduler().runTaskLater(GelenceSignShop.getInstance(), () -> {
            sign.getSide(Side.FRONT).setLine(0, line1);
            sign.getSide(Side.FRONT).setLine(3, line4);
            sign.update();
        }, 4L);
    }

    private static String formatLine1(ShopSign shop) {
        return shop.isWaiting() ? ChatColor.YELLOW + "[SHOP]" : ChatColor.BLUE + "[SHOP]";
    }

    private static String formatLine4(ShopSign shop) {
        String line4 =  ChatColor.YELLOW + (shop.isBuySign() ? "buy: " : "sell: ") + ChatColor.RESET;

        double value = shop.getValue();
        if(value == 0) {
            line4 += shop.isBuySign() ? "FREE" : "DONATE";
        }
        else {
            line4 += PriceParser.formatPrice(value) + " GV";
        }

        return line4;
    }
}
