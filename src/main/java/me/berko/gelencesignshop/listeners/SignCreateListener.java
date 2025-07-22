package me.berko.gelencesignshop.listeners;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.util.PriceParser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreateListener implements Listener {
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        String line0 = ChatColor.stripColor(event.getLine(0));
        if (line0 == null || !line0.equalsIgnoreCase("[SHOP]")) return;

        String priceLine = event.getLine(3);
        if (priceLine == null) return;

        Player player = event.getPlayer();

        double value;
        boolean isBuySign;

        // determine if the shop is buy/sell and get raw price string
        String rawPrice;
        if (priceLine.startsWith("buy:")) {
            isBuySign = true;
            rawPrice = priceLine.substring(4).trim();
        } else if (priceLine.startsWith("sell:")) {
            isBuySign = false;
            rawPrice = priceLine.substring(5).trim();
        } else {
            player.sendMessage(ChatColor.RED + "Line 4 must start with 'buy:' or 'sell:'.");
            return;
        }

        try {
            value = PriceParser.parsePrice(rawPrice);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid price format on line 4.");
            return;
        }

        event.setLine(0, ChatColor.YELLOW + "[SHOP]");

        String formattedPriceLine;
        if(isBuySign) {
            formattedPriceLine = ChatColor.YELLOW + "buy: " +
                    ChatColor.RESET + (value == 0 ? "FREE" : PriceParser.formatPrice(value) + " GV");
        } else {
            formattedPriceLine = ChatColor.YELLOW + "sell: " +
                    ChatColor.RESET + (value == 0 ? "DONATE" : PriceParser.formatPrice(value) + " GV");
        }
        event.setLine(3, formattedPriceLine);

        GelenceSignShop.getInstance()
                .getShopSignManager()
                .markAsWaiting(event.getBlock().getLocation(), value, isBuySign);

        player.sendMessage(ChatColor.GREEN + "Shop sign created! Right-click it with the desired " +
                ChatColor.YELLOW + "item in your main hand" +
                ChatColor.GREEN + " to bind.");
    }


}
