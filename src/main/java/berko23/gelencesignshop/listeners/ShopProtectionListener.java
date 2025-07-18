package berko23.gelencesignshop.listeners;

import berko23.gelencesignshop.GelenceSignShop;
import berko23.gelencesignshop.shop.ShopSignManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ShopProtectionListener implements Listener {

    private final ShopSignManager manager = GelenceSignShop.getInstance().getShopSignManager();

    @EventHandler
    public void onSignBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (manager.isShop(loc)) {
            Player player = event.getPlayer();

            if (isHoldingGoldAxe(player)) {
                manager.remove(loc);
                player.sendMessage(ChatColor.YELLOW + "Shop sign removed.");
                return;
            }

            // save sign tex before it disappears
            String[] lines = new String[4];
            BlockState state = block.getState();
            if (state instanceof Sign signBefore) {
                lines = signBefore.getLines().clone();
            }

            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You can't break this shop sign!");

            // rewrite text
            String[] finalLines = lines;
            Bukkit.getScheduler().runTaskLater(GelenceSignShop.getInstance(), () -> {
                BlockState newState = block.getState();
                if (newState instanceof Sign signAfter) {
                    for (int i = 0; i < 4; i++) {
                        signAfter.setLine(i, finalLines[i]);
                    }
                    signAfter.update();
                }
            }, 4L); // 4 tick delay
        }
    }

    private boolean isHoldingGoldAxe(Player player) {
        return player.getInventory().getItemInMainHand().getType() == Material.GOLDEN_AXE;
    }
}
