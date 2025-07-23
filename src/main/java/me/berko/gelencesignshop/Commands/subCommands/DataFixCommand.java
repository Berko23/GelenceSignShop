package me.berko.gelencesignshop.Commands.subCommands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSignManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DataFixCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        ShopSignManager manager = GelenceSignShop.getInstance().getShopSignManager();
        int removed = manager.removeInvalidShops();

        if (sender instanceof Player p) {
            p.sendMessage("§aData fix complete! Removed " +
                    (removed > 0 ?
                            ("Removed " + removed + " invalid shops.") :
                            ("No invalid shops found.")));
        }

        if(removed > 0) {
            Bukkit.getLogger().info("[GelenceSignShop] Data fix complete! " + "Removed " + removed + " invalid shops.");
        }
    }
}
