package berko23.gelencesignshop.listeners;

import berko23.gelencesignshop.GelenceSignShop;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignCreateListener implements Listener {
//FIXME: sign protection does not work after creation (it prompts the right click request, but the sign doesn't get waxed)

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        String line0 = ChatColor.stripColor(event.getLine(0));
        if (line0 == null || !line0.equalsIgnoreCase("[shop]")) {
            return;
        }

        Player player = event.getPlayer();

        String priceLine = event.getLine(3);
        Double buyPrice = null;
        Double sellPrice = null;

        try {
            assert priceLine != null;
            String[] prices = priceLine.trim().split("\\s+");
            for (String price : prices) {
                double value = Double.parseDouble(price);
                if (price.startsWith("-")) {
                    sellPrice = -value;
                } else {
                    buyPrice = value;
                }
            }
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Invalid price format on line 4.");
            return;
        }

        event.setLine(0, ChatColor.YELLOW + "[SHOP]");


        GelenceSignShop.getInstance()
                .getShopSignManager()
                .markAsWaiting(event.getBlock().getLocation(), buyPrice, sellPrice);

        // set the sign waxed
        BlockState state = event.getBlock().getState();
        if (state instanceof Sign sign) {
            try {
                sign.setWaxed(true);
                sign.update();
            } catch (NoSuchMethodError | NoClassDefFoundError err) {
                player.sendMessage(ChatColor.YELLOW + "Warning: Waxing sign not supported on this server version.");
            }
        }

        player.sendMessage(ChatColor.GREEN + "Shop sign created! Right-click it with the desired " +
                ChatColor.YELLOW + "item in your main hand" +
                ChatColor.GREEN + " to bind.");
    }
}
