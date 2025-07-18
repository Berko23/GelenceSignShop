package berko23.gelencesignshop.listeners;

import berko23.gelencesignshop.GelenceSignShop;
import berko23.gelencesignshop.shop.ShopSign;
import berko23.gelencesignshop.shop.ShopSignManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SignInteractListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Block block = event.getClickedBlock();
        if (block == null || !(block.getState() instanceof Sign sign)) return;

        ShopSignManager manager = GelenceSignShop.getInstance().getShopSignManager();
        Player player = event.getPlayer();
        ShopSign shop = manager.get(block.getLocation());

        if (shop == null) return;

        if (shop.isWaiting()) {
            ItemStack inHand = player.getInventory().getItemInMainHand();
            if (inHand.getType() == Material.AIR) {
                player.sendMessage(ChatColor.RED + "Hold an item to bind to the shop.");
                return;
            }

            sign.setLine(0, ChatColor.DARK_BLUE + "[SHOP]");
            sign.update();
            manager.bindItem(block.getLocation(), inHand);
            player.sendMessage(ChatColor.GREEN + "Shop successfully bound to " + inHand.getType());
        } else {
            // TODO: később jön a vásárlás/eladás logika
        }
    }
}