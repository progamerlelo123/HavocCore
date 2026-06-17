package com.havoccore.economy;

import com.havoccore.HavocCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class EconomyManager {

    private final HavocCore plugin;
    private final HashMap<UUID, Long> balances = new HashMap<>();
    private File configFile;
    private FileConfiguration balanceConfig;

    public EconomyManager(HavocCore plugin) {
        this.plugin = plugin;
        loadBalances();
    }

    public synchronized long getBalance(UUID uuid) {
        return balances.getOrDefault(uuid, 0L);
    }

    public synchronized void addMoney(UUID uuid, long amount) {
        balances.put(uuid, getBalance(uuid) + amount);
    }

    public synchronized boolean removeMoney(UUID uuid, long amount) {
        if (getBalance(uuid) < amount) return false;
        balances.put(uuid, getBalance(uuid) - amount);
        return true;
    }

    public void loadBalances() {
        configFile = new File(plugin.getDataFolder(), "balances.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            try { configFile.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        balanceConfig = YamlConfiguration.loadConfiguration(configFile);
        for (String key : balanceConfig.getKeys(false)) {
            try { balances.put(UUID.fromString(key), balanceConfig.getLong(key)); } catch (Exception ignored) {}
        }
    }

    public synchronized void saveBalances() {
        if (balanceConfig == null || configFile == null) return;
        for (UUID uuid : balances.keySet()) {
            balanceConfig.set(uuid.toString(), balances.get(uuid));
        }
        try { balanceConfig.save(configFile); } catch (IOException e) { e.printStackTrace(); }
    }
}
