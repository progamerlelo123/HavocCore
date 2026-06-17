package com.havoccore.commands;

import com.havoccore.HavocCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TradeCommands implements CommandExecutor {

    private final HavocCore plugin;

    public TradeCommands(HavocCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use trade and auction commands.");
            return true;
        }

        // Handle Auction House Command
        if (command.getName().equalsIgnoreCase("ah")) {
            plugin.getAuctionManager().openAuctionHouse(player);
            return true;
        }

        // Handle Player Trading Command
        if (command.getName().equalsIgnoreCase("trade")) {
            if (args.length == 0) {
                player.sendMessage("§cUsage: /trade <player> or /trade accept");
                return true;
            }

            if (args[0].equalsIgnoreCase("accept")) {
                plugin.getTradeManager().acceptTrade(player);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage("§cPlayer not found or offline.");
                return true;
            }

            if (target.getUniqueId().equals(player.getUniqueId())) {
                player.sendMessage("§cYou cannot trade with yourself!");
                return true;
            }

            plugin.getTradeManager().sendTradeRequest(player, target);
            return true;
        }

        return false;
    }
}
