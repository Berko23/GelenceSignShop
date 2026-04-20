package me.berko.gelencesignshop.Commands.tabcompleters;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.Commands.subCommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class SignShopTabCompleter implements TabCompleter {

    private final Map<String, SubCommand> subcommands = new HashMap<>();

    public SignShopTabCompleter() {
        subcommands.put("help", new HelpCommand());
        subcommands.put("helpsetup", new HelpSetupCommand());
        subcommands.put("datareload", new DataReloadCommand());
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
            List<String> arg2 = new ArrayList<>();

            if(args[0].equals("helpsetup")) {
                String partial = args[1].toLowerCase();
                List<String> options = Arrays.asList("1", "2", "3");
                for(String option : options) {
                    if(option.startsWith(partial)) {
                        arg2.add(option);
                    }
                }
            }

            arg2.add("");
            return arg2;
        }

        return Collections.emptyList();
    }
}
