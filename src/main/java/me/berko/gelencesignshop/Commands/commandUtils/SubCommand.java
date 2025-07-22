package me.berko.gelencesignshop.Commands.commandUtils;

import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public interface SubCommand {
    void execute(CommandSender sender, String[] args);
}
