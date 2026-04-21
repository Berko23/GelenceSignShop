package me.berko.gelencesignshop.Commands.subCommands;

import me.berko.gelencesignshop.Commands.commandUtils.SubCommand;
import me.berko.gelencesignshop.GelenceSignShop;
import me.berko.gelencesignshop.shop.ShopSign;
import me.berko.gelencesignshop.shop.ShopSignManager;
import me.berko.gelencesignshop.util.PriceParser;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EditCommand implements SubCommand {
    private final ShopSignManager shopManager = GelenceSignShop.getInstance().getShopSignManager();

    private final String helpUsageMessage = "\n"
            + ChatColor.GRAY + "Use "
            + ChatColor.YELLOW + "/signshop edit help "
            + ChatColor.GRAY + "for more information.";

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            return;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.GOLD + "Usage: /signshop edit <option> [<change_to>]" + helpUsageMessage);
            return;
        }

        String option = args[1].toLowerCase();

        switch (option) {
            case "price":
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.GOLD + "Usage: /signshop edit price <newPrice>" + helpUsageMessage);
                    return;
                }
                runEditPrice(player, args[2]);
                break;

            case "buysell":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.GOLD + "Usage: /signshop edit buysell" + helpUsageMessage);
                    return;
                }
                runEditBuySell(player);
                break;

            case "item":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.GOLD + "Usage: /signshop edit item" + helpUsageMessage);
                    return;
                }
                runEditItem(player);
                break;

            case "help":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.GOLD + "Usage: /signshop edit help");
                    return;
                }
                runEditHelp(player);
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown option: " + option + helpUsageMessage);
        }
    }

    private void runEditPrice(Player sender, String newPriceString) {
        ShopSign shop = getTargetShopSign(sender);
        if (shop == null) return;

        double newPrice;
        try {
            newPrice = PriceParser.parsePrice(newPriceString);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Invalid price format: " + newPriceString);
            return;
        }

        shopManager.changeValue(shop, newPrice);
    }

    private void runEditBuySell(Player sender) {
        ShopSign shop = getTargetShopSign(sender);
        if (shop == null) return;

        shopManager.changeBuySell(shop, !shop.isBuySign());
    }

    private void runEditItem(Player sender) {
        ShopSign shop = getTargetShopSign(sender);
        if (shop == null) return;

        shopManager.unbindItem(shop);
    }

    private void runEditHelp(Player sender) {
        sender.sendMessage("§6==== '/signshop edit' guide ====");
        sender.sendMessage("§6General info§f: This command allows you to edit existing shop signs by looking at them and using the specified options. You must be looking directly at the shop sign within a range of 5 blocks for the command to work.");
        sender.sendMessage("§6/signshop edit help§f: Displays this help information.");
        sender.sendMessage("§6/signshop edit price <new_price>§f: Edit the sell/buy price of the shop sign you are looking at.");
        sender.sendMessage("    §7note: you can also use K/M/B annotations for prices, and setting price to 0 will make the sign free/donate (just like in regular sign setup)");
        sender.sendMessage("§6/signshop edit buysell§f: Switches the shop sign you are looking at from buy to sell or vice versa.");
        sender.sendMessage("§6/signshop edit item§f: Unbinds the currently bound item from the shop sign you are looking at, allowing you to bind a new item by right-clicking the sign with the desired item in your main hand.");
    }

    private ShopSign getTargetShopSign(Player player) {
        Block targetBlock = player.getTargetBlockExact(5);

        if (targetBlock == null) {
            player.sendMessage(ChatColor.RED + "You are not looking at a shop sign.");
            return null;
        }

        if (!(targetBlock.getState() instanceof Sign sign && shopManager.isShop(sign.getLocation()))) {
            player.sendMessage(ChatColor.RED + "You are not looking at a shop sign.");
            return null;
        }

        return shopManager.getShopSign(sign.getLocation());
    }
}
