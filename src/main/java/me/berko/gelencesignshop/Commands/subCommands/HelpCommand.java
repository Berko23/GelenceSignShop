package me.berko.gelencesignshop.Commands.subCommands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player p){
            p.sendMessage("§6==== Sign Shop possible commands ===");
            p.sendMessage("§6/signshop help§f: Displays this help information.");
            p.sendMessage("§6/signshop helpsetup§f: Displays a step-by-step guide about setting up sign shops");
            p.sendMessage("§6/signshop datareload§f: Reloads the sign-shop datas from the save file");
            p.sendMessage("§6/signshop datacheck§f: Check if there are saved sign-shops in the memory, that do not exist in the world and displays their coordinates.");
            p.sendMessage("§6/signshop datacheck§f: Deletes sign-shops, that are present in the memory but don't exist in the world.");
        }
    }
}
