package me.berko.gelencesignshop.Commands.tabcompleters;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.Commands.subCommands.DataCheckCommand;
import me.berko.gelencesignshop.Commands.subCommands.DataFixCommand;
import me.berko.gelencesignshop.Commands.subCommands.HelpSetupCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class SignShopTabCompleter implements TabCompleter {

    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public SignShopTabCompleter() {
        subcommands.put("helpsetup", new HelpSetupCommand());
        subcommands.put("datafix", new DataFixCommand());
        subcommands.put("datacheck", new DataCheckCommand());
    }

    public Map<String, SubCommand> getSubcommands() {
        return subcommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String partial = args[0].toLowerCase();
            return subcommands.keySet().stream()
                    .filter(cmd -> cmd.startsWith(partial))
                    .sorted()
                    .toList();
        }

        if (args.length > 1) {
            List<String> nothing = new ArrayList<>();
            nothing.add("");
            return nothing;
        }

        return Collections.emptyList();
    }
}
