package me.berko.gelencesignshop.listeners;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSign;
import me.berko.gelencesignshop.util.PriceParser;
import me.berko.gelencesignshop.util.SignDisplayManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
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

        // If we reach this point, the sign is valid. Register new sign, mark as waiting for item binding, and format the sign text.

        // Cancel the event (we will update the sign manually) to avoid any unwanted update to the sign text.
        event.setCancelled(true);

        // At this point the lines only exist in the event, so we need to set them on the actual sign block,
        //   otherwise the SignDisplayManager will overwrite them when it tries to update only line 1 and line 4.
        //   (the update() function updates the whole sign, and line 2 and 3 would be lost if we don't set them here.)
        Sign sign = (Sign) event.getBlock().getState();
        for (int i = 0; i < 4; i++) {
            sign.getSide(Side.FRONT).setLine(i, event.getLine(i));
        }
        sign.update();

        ShopSign newShop = new ShopSign(event.getBlock().getLocation(), value, isBuySign);

        SignDisplayManager.updateSign(newShop); // format line 1 and line 4

        GelenceSignShop.getInstance()
                .getShopSignManager()
                .markAsWaiting(newShop);

        player.sendMessage(ChatColor.GREEN + "Shop sign created! Right-click it with the desired " +
                ChatColor.YELLOW + "item in your main hand" +
                ChatColor.GREEN + " to bind.");
    }


}
