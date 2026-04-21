package me.berko.gelencesignshop.listeners;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSign;
import me.berko.gelencesignshop.util.PriceParser;
import me.berko.gelencesignshop.util.SignDisplayManager;
import org.bukkit.ChatColor;
import org.bukkit.block.HangingSign;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SignCopyPasteListener implements Listener {
    @EventHandler
    void blockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getState() instanceof Sign sign) {
            // ignore hanging signs
            if(sign instanceof HangingSign) return;

            // ignore if line 1 doesn't say "[shop]" (case-insensitive)
            String line1 = stripColors(sign.getSide(Side.FRONT).getLine(0));
            if (!line1.equalsIgnoreCase("[shop]")) return;

            // validate line 4
            String line4 = stripColors(sign.getSide(Side.FRONT).getLine(3));
            if (!isValidLine4(line4)) return;

            // If everything is valid, register new sign and mark as waiting for item binding
            ShopSign newShop = new ShopSign(event.getBlock().getLocation(), extractPrice(line4), isBuySign(line4));
            GelenceSignShop.getInstance()
                    .getShopSignManager()
                    .markAsWaiting(newShop);

            // Reformat line 1 after a short delay to ensure it overwrites any copied text.
            SignDisplayManager.updateSign(newShop);

            // Send success message to player
            event.getPlayer().sendMessage(ChatColor.GREEN + "Shop sign pasted successfully! Right-click it with the desired " +
                    ChatColor.YELLOW + "item in your main hand" +
                    ChatColor.GREEN + " to bind.");
        }
    }

    private static String stripColors(String input) {
        if (input == null) return null;
        return input.replaceAll("(?i)[§&][0-9A-FK-ORX]", "");
    }

    private static boolean isValidLine4(String line) {
        if (line == null) return false;

        line = line.trim();

        // must start with buy: or sell:
        if (!(line.startsWith("buy:") || line.startsWith("sell:"))) {
            return false;
        }

        String type = line.startsWith("buy:") ? "buy" : "sell";
        String value = line.substring(type.length() + 1).trim(); // after "buy:" or "sell:"

        // FREE / DONATE rules
        if (value.equalsIgnoreCase("FREE")) {
            return type.equals("buy");
        }

        if (value.equalsIgnoreCase("DONATE")) {
            return type.equals("sell");
        }

        // Otherwise: price
        return isValidPrice(value);
    }

    private static boolean isValidPrice(String value) {
        value = value.trim();

        // Remove optional "GV"
        if (value.toUpperCase().endsWith("GV")) {
            value = value.substring(0, value.length() - 2).trim();
        }

        // Check suffix
        char lastChar = value.charAt(value.length() - 1);
        if ("kKmMbB".indexOf(lastChar) != -1) {
            value = value.substring(0, value.length() - 1);
        }

        // Now it should be a number
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private double extractPrice(String input) {
        if (input == null) return -1;

        input = stripColors(input).trim();

        if (!(input.startsWith("buy:") || input.startsWith("sell:"))) {
            return -1;
        }

        String type = input.startsWith("buy:") ? "buy" : "sell";
        String value = input.substring(type.length() + 1).trim();

        if (value.equalsIgnoreCase("FREE") || value.equalsIgnoreCase("DONATE")) {
            return 0;
        }

        if (value.toUpperCase().endsWith("GV")) {
            value = value.substring(0, value.length() - 2).trim();
        }

        return PriceParser.parsePrice(value);
    }

    private boolean isBuySign(String line4) {
        return line4.toLowerCase().startsWith("buy:");
    }
}
