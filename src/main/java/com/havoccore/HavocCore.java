package com.havoccore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.havoccore.economy.EconomyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public final class HavocCore extends JavaPlugin {

    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        this.economyManager = new EconomyManager(this);

        // Optimized Playtime Loop (72000L Ticks = 1 Hour)
        Bukkit.getScheduler().runTaskTimer(this, task -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;
            
            for (Player player : Bukkit.getOnlinePlayers()) {
                economyManager.addMoney(player.getUniqueId(), 500L);
                player.sendMessage(Component.text("+500 Havocks Hourly Reward!", NamedTextColor.GREEN));
            }
        }, 72000L, 72000L);

        getLogger().info("HavocCore v1.0 cleanly booted on Paper 1.21.");
    }

    @Override
    public void onDisable() {
        if (economyManager != null) {
            economyManager.saveBalances();
        }
    }

    public EconomyManager getEconomyManager() { return economyManager; }
}
