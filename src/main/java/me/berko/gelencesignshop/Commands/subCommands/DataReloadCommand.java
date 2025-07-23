package me.berko.gelencesignshop.Commands.subCommands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.GelenceSignShop;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DataReloadCommand implements SubCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        GelenceSignShop.getInstance().getShopSignManager().loadAllSigns();

        if (sender instanceof Player p) {
            p.sendMessage("§eReloaded all sign-shops.");
        }

        Bukkit.getLogger().info("[GelenceSignShop] Attempted to reload sign-shops from save file. (result unknown)");
    }
}
