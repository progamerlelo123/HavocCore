package com.havoccore.economy;

import com.havoccore.HavocCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AuctionManager {

    private final HavocCore plugin;
    private final ConcurrentHashMap<UUID, AuctionItem> listings = new ConcurrentHashMap<>();

    public AuctionManager(HavocCore plugin) {
        this.plugin = plugin;
    }

    public void openAuctionHouse(Player player) {
        Inventory gui = Bukkit.createInventory(player, 54, Component.text("Havoc Auction House", NamedTextColor.DARK_RED));
        player.openInventory(gui);
    }

    public static class AuctionItem {
        private final UUID seller;
        private final long price;

        public AuctionItem(UUID seller, long price) {
            this.seller = seller;
            this.price = price;
        }

        public UUID getSeller() { return seller; }
        public long getPrice() { return price; }
    }
}
