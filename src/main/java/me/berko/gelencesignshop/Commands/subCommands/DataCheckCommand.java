package me.berko.gelencesignshop.Commands.subCommands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.GelenceSignShop;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class DataCheckCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        List<Location> invalid = GelenceSignShop.getInstance().getShopSignManager().getInvalidShopLocations();

        if (invalid.isEmpty()) {
            if(sender instanceof Player p) {
                p.sendMessage("§aData check complete! No invalid shops found!");
            }
            return;
        }

        if(sender instanceof Player p) {
            p.sendMessage("§eData check complete! Found " + invalid.size() + " invalid shop(s):");
            for (Location loc : invalid) {
                p.sendMessage("§7> " + Objects.requireNonNull(loc.getWorld()).getName() + " @ " +
                        loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
            }
        }

        Bukkit.getLogger().info("[GelenceSignShop] Data check complete! Found " + invalid.size() + " invalid shop(s):");
        for (Location loc : invalid) {
            Bukkit.getLogger().info("> " + Objects.requireNonNull(loc.getWorld()).getName() + " @ " +
                    loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        }
    }
}
