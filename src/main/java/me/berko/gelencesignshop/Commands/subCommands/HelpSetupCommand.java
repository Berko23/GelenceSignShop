package me.berko.gelencesignshop.Commands.subCommands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpSetupCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        if (args.length <= 1 || args[1].equals("1")) {
            player.sendMessage("§6=== SignShop Setup Help 1/3 ===");
            player.sendMessage("§6          Basic setup");
            player.sendMessage("§e1. §fPlace a sign anywhere in the world.");
            player.sendMessage("§e2. §f1st line: §b[shop]");
            player.sendMessage("§e3. §f2nd and 3rd lines are optional (you can write anything you want)");
            player.sendMessage("§e4. §fWrite the price in the 4th line:");
            player.sendMessage("    §7example: §abuy: 123 §7or §csell: 500.5");
            player.sendMessage("    §7note: A sign can only buy or only sell.");
            player.sendMessage("§e5. §fRight-click the sign with the §6desired item(s) in your main hand§f.");
            player.sendMessage("    §7note: The sign will remember the amount of items too!");
            player.sendMessage("§e6. §fDone! From now on, players can buy/sell the selected item(s) at that sign.");
            player.sendMessage("§e[i]. §fYou can destroy shop signs with a §6golden axe §f.");
            player.sendMessage("§6=== SignShop Setup Help 1/3 ===");
            player.sendMessage("");
            player.sendMessage("§6Use §f/signshop helpsetup 2 §6for more information");
        } else {
            switch (args[1]) {
                case "2":
                    player.sendMessage("§6=== SignShop Setup Help 2/3 ===");
                    player.sendMessage("§6         Annotations");
                    player.sendMessage("§e- §fYou can also define a price using the following annotations:");
                    player.sendMessage("    §f- §aK §ffor x1.000 ");
                    player.sendMessage("    §f- §aM §ffor x1.000.000 ");
                    player.sendMessage("    §f- §aB §ffor x1.000.000.000 ");
                    player.sendMessage("    §7example: §abuy: 12.4k §7is the same as §abuy: 12400");
                    player.sendMessage("    §7note: These annotations are not case-sensitive.");
                    player.sendMessage("§e- §fBy setting the buy/sell price to 0, will declare that sign to FREE or DONATE ");
                    player.sendMessage("§6=== SignShop Setup Help 2/3 ===");
                    player.sendMessage("");
                    player.sendMessage("§6Use §f/signshop helpsetup 3 §6for more information");
                    break;
                case "3":
                    player.sendMessage("§6=== SignShop Setup Help 3/3 ===");
                    player.sendMessage("§6         Coloring signs");
                    player.sendMessage("§f You can color and make shop signs glow simply by coloring them or applying inc sac before writing the [shop] text in the first line.");
                    player.sendMessage("    §7note: You will not be able to modify the sign after the [shop] text turned yellow or blue.");
                    player.sendMessage("§6=== SignShop Setup Help 3/3 ===");
                    break;
                default:
                    player.sendMessage("§6Pleas use a number from 1 to 3");
            }
        }
    }
}
