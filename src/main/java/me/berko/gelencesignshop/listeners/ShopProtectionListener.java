package me.berko.gelencesignshop.listeners;

import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSignManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.block.sign.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class ShopProtectionListener implements Listener {

    private final ShopSignManager manager = GelenceSignShop.getInstance().getShopSignManager();

    @EventHandler
    public void onSignBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Location loc = block.getLocation();

        if (manager.isShop(loc)) {
            Player player = event.getPlayer();

            if (isHoldingGoldAxe(player)) {
                player.sendMessage(ChatColor.YELLOW + "Shop sign removed.");
                manager.remove(loc); // remove from file too
                return;
            }

            // save sign tex before it disappears
            String[] lines = new String[4];
            BlockState state = block.getState();
            if (state instanceof Sign signBefore) {
                lines = signBefore.getSide(Side.FRONT).getLines().clone();
            }

            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "You can't break this shop sign!");

            // rewrite text
            // CAN NOT use SignDisplayManager here, because this function needs different logic for
            //     saving the text before the break and rewriting it after the break.
            String[] finalLines = lines;
            Bukkit.getScheduler().runTaskLater(GelenceSignShop.getInstance(), () -> {
                BlockState newState = block.getState();
                if (newState instanceof Sign signAfter) {
                    for (int i = 0; i < 4; i++) {
                        signAfter.getSide(Side.FRONT).setLine(i, finalLines[i]);
                    }
                    signAfter.update();
                }
            }, 4L); // 4 tick delay
        }
    }

    @EventHandler
    public void onSignHolderBreak(BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();

        // check if the block on top is a sign shop
        Block blockAbove = brokenBlock.getRelative(BlockFace.UP);
        if(manager.isShop(blockAbove.getLocation())) {
            BlockState state = blockAbove.getState();

            // Check if the block above is a standing sign (not a wall sign)
            if (state instanceof Sign sign && !(sign.getBlockData() instanceof WallSign)) {
                event.setCancelled(true);
                sendPlayerMessage(event.getPlayer());
                return;
            }
        }

        List<BlockFace> sides = new ArrayList<>();
        sides.add(BlockFace.NORTH);
        sides.add(BlockFace.EAST);
        sides.add(BlockFace.SOUTH);
        sides.add(BlockFace.WEST);

        // Check the 4 sides for wall sign-shops
        for (BlockFace face : sides) {
            Block relative = brokenBlock.getRelative(face);
            if(!manager.isShop(relative.getLocation())){     // skip if the adjacent block is not a shop
                continue;
            }

            BlockState state = relative.getState();
            if (state instanceof Sign signState) {
                BlockData data = signState.getBlockData();

                // We only care about wall signs (attached to blocks)
                if (data instanceof WallSign wallSign) {
                    // Check if the sign is attached to the broken block
                    BlockFace attachedTo = wallSign.getFacing().getOppositeFace();
                    if (relative.getRelative(attachedTo).equals(brokenBlock)) {
                        event.setCancelled(true);
                        sendPlayerMessage(event.getPlayer());
                        return;
                    }
                }
            }
        }
    }



    private boolean isHoldingGoldAxe(Player player) {
        return player.getInventory().getItemInMainHand().getType() == Material.GOLDEN_AXE;
    }

    private void sendPlayerMessage(Player p){
        p.sendMessage(ChatColor.RED + "You can't break a block, that is holding a shop sign!");
    }
}
