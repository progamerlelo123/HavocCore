package com.havoccore;

import com.havoccore.economy.AuctionManager;
import com.havoccore.economy.EconomyManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HavocCore extends JavaPlugin {

    private EconomyManager economyManager;
    private AuctionManager auctionManager;

    @Override
    public void onEnable() {
        // Initialize Core Systems
        this.economyManager = new EconomyManager(this);
        this.auctionManager = new AuctionManager(this);

        // Start background tasks (Hourly payout loop)
        Bukkit.getScheduler().runTaskTaskTimer(this, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                economyManager.addBalance(player.getUniqueId(), 500);
                player.sendMessage("§a[HavocEconomy] You received your hourly 500 Havocks!");
            });
        }, 0L, 72000L); // 72000 ticks = 1 hour

        getLogger().info("HavocCore Anarchy Economy successfully enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HavocCore shutting down safely.");
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public AuctionManager getAuctionManager() {
        return auctionManager;
    }
}
