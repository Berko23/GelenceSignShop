package me.berko.gelencesignshop.Commands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.Commands.subCommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class SignShopCommand implements CommandExecutor {

    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public SignShopCommand() {
        subcommands.put("help", new HelpCommand());
        subcommands.put("helpsetup", new HelpSetupCommand());
        subcommands.put("datareload", new DataReloadCommand());
        subcommands.put("datafix", new DataFixCommand());
        subcommands.put("datacheck", new DataCheckCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("§eUsage: /signshop <subcommand>");
            return true;
        }

        SubCommand sub = subcommands.get(args[0].toLowerCase());
        if (sub == null) {
            sender.sendMessage("§cUnknown command: /signshop " + args[0]);
            return true;
        }

        sub.execute(sender, args);
        return true;
    }
}
