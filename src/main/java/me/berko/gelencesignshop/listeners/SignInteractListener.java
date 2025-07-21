package me.berko.gelencesignshop.listeners;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSign;
import me.berko.gelencesignshop.shop.ShopSignManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SignInteractListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null || !(block.getState() instanceof Sign sign)) {
            return;
        }

        ShopSignManager manager = GelenceSignShop.getInstance().getShopSignManager();
        Player player = event.getPlayer();
        ShopSign shop = manager.get(block.getLocation());

        if (shop == null) {
            return;
        }

        event.setCancelled(true); // CANCEL the event if hte target is a shop sign

        if (shop.isWaiting()) {
            ItemStack inHand = player.getInventory().getItemInMainHand();
            if (inHand.getType() == Material.AIR) {
                player.sendMessage(ChatColor.RED + "Hold an item in your main hand to bind to the shop.");
                return;
            }

            sign.getSide(Side.FRONT).setLine(0, ChatColor.BLUE + "[SHOP]");
            sign.update();
            manager.bindItem(block.getLocation(), inHand);

            // set the sign waxed
            try {
                sign.setWaxed(true);
                sign.update();
            } catch (NoSuchMethodError | NoClassDefFoundError err) {
                player.sendMessage(ChatColor.YELLOW + "Warning: Waxing sign not supported on this server version.");
            }

            player.sendMessage(ChatColor.GREEN + "Shop successfully bound to " + ChatColor.YELLOW + inHand.getType());
        } else {
            // TODO: later here will be the buy/sell logic
        }
    }
}