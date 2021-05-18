package io.github.thebusybiscuit.slimefun4.core.commands.subcommands;

import io.github.thebusybiscuit.slimefun4.core.commands.SlimefunCommand;
import io.github.thebusybiscuit.slimefun4.core.commands.SubCommand;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.ItemStackWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

class DebugCommand extends SubCommand {

    DebugCommand(SlimefunPlugin plugin, SlimefunCommand cmd) {
        super(plugin, cmd, "debug", false);
    }

    @Override
    public void onExecute(CommandSender sender, String[] args) {
        if (args.length == 2) {
            switch (args[1]) {
                case "true":
                    ItemStackWrapper.WRAP_DETECTION = true;
                    break;
                case "false":
                    ItemStackWrapper.WRAP_DETECTION = false;
                    break;
                default:
                    if (args[0].isEmpty()) {
                        break;
                    } else {
                        sender.sendMessage("unknown value: " + args[0]);
                    }
                    return;
            }
            sender.sendMessage("[Slimefun] [Debug] Wrap detection changed!");
            plugin.getLogger().info("[Slimefun] Wrap detection changed!");
            plugin.getLogger().info("[Slimefun] Wrap detection: " + ItemStackWrapper.WRAP_DETECTION);
        }
        sender.sendMessage("[Slimefun] [Debug] Wrap detection = " + ItemStackWrapper.WRAP_DETECTION);
    }

}
